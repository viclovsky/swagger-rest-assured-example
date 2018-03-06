# Swagger Rest-assured integration example #

This is entry point for 
[Pull Request](https://github.com/swagger-api/swagger-codegen/pull/7492)

## Quick Start
* Add maven plugin with version 2.4.0-SNAPSHOT into pom.xml. Don't forget <inputSpec> in configuration tag with swagger specification:
```xml
 <plugin>
     <groupId>io.swagger</groupId>
     <artifactId>swagger-codegen-maven-plugin</artifactId>
     <!--Need in your local maven repo-->
     <version>2.4.0-SNAPSHOT</version>
     <executions>
         <execution>
             <goals>
                 <goal>generate</goal>
             </goals>
             <configuration>
                 <!--Your input swagger spec-->
                 <inputSpec>http://petstore.swagger.io/v2/swagger.json</inputSpec>
                 <output>${project.build.directory}/generated-sources/swagger</output>
                 <language>java</language>
                 <configOptions>
                     <dateLibrary>java8</dateLibrary>
                 </configOptions>
                 <library>rest-assured</library>
                 <!--Generate test templates-->
                 <generateApiTests>true</generateApiTests>
                 <generateApiDocumentation>false</generateApiDocumentation>
                 <generateModelDocumentation>false</generateModelDocumentation>
                 <apiPackage>${default.package}.api</apiPackage>
                 <modelPackage>${default.package}.model</modelPackage>
                 <invokerPackage>${default.package}</invokerPackage>
             </configuration>
         </execution>
     </executions>
 </plugin>
```
See [swagger-codegen-maven-plugin](https://github.com/swagger-api/swagger-codegen/tree/master/modules/swagger-codegen-maven-plugin) for detail configuration.

* Add necessary dependencies for API client:
```xml
     <dependency>
         <groupId>io.swagger</groupId>
         <artifactId>swagger-annotations</artifactId>
         <version>${swagger-core-version}</version>
     </dependency>
     <dependency>
         <groupId>io.rest-assured</groupId>
         <artifactId>rest-assured</artifactId>
         <version>${rest-assured.version}</version>
     </dependency>
     <dependency>
         <groupId>com.google.code.gson</groupId>
         <artifactId>gson</artifactId>
         <version>${gson-version}</version>
     </dependency>
     <dependency>
         <groupId>io.gsonfire</groupId>
         <artifactId>gson-fire</artifactId>
         <version>${gson-fire-version}</version>
     </dependency>
     <dependency>
         <groupId>com.squareup.okio</groupId>
         <artifactId>okio</artifactId>
         <version>${okio-version}</version>
     </dependency>
```
* Add necessary dependencies for 'templates' for tests:
```xml
     <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>${junit.version}</version>
     </dependency>
```

* Run ```mvn clean compile``` for generation of API client

* As soon as generated code has been placed in target (```/swagger-rest-assured-example/example-api-client/target/generated-sources/swagger/src/main```), you can use it to write your tests. Moreover 'templates' for tests will be generated (```/swagger-rest-assured-example/example-api-client/target/generated-sources/swagger/src/test```).

* The simplest test with junit4 looks like below (see [SimpleJunit4Test](https://github.com/viclovsky/swagger-rest-assured-example/blob/master/example-api-tests/src/test/java/ru/vicdev/example/SimpleJunit4Test.java)):
```java
    private ApiClient api;
    
    @Before
    public void createApi() {
        api = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(
                () -> new RequestSpecBuilder().setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(gson())))
                        .addFilter(new ErrorLoggingFilter())
                        .setBaseUri("http://petstore.swagger.io:80/v2"))).store();
    }

    @Test
    public void getInventoryTest() {
        Map<String, Integer> inventory = api.getInventory().executeAs(validatedWith(shouldBeCode(SC_OK)));
        assertThat(inventory.keySet().size(), greaterThan(0));
    }
```
* Simple junit5 test with inject client was added for demonstration (see [SimpleJunit5Test](https://github.com/viclovsky/swagger-rest-assured-example/blob/master/example-api-tests/src/test/java/ru/vicdev/example/SimpleJunit5Test.java))

## Links
* [swagger-codegen-maven-plugin](https://github.com/swagger-api/swagger-codegen/tree/master/modules/swagger-codegen-maven-plugin)
* [junit5](https://junit.org/junit5/)
* [junit5-extensions](https://github.com/JeffreyFalgout/junit5-extensions/tree/master/guice-extension)

