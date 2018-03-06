package ru.vicdev.example.provider;

import com.google.inject.Provider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import ru.vicdev.example.swagger.client.ApiClient;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static ru.vicdev.example.swagger.client.GsonObjectMapper.gson;

public class ExampleApiProvider implements Provider<ApiClient> {

    @Override
    public ApiClient get() {
        return ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(
                () -> new RequestSpecBuilder().setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(gson())))
                        .addFilter(new ErrorLoggingFilter())
                        .setBaseUri("http://petstore.swagger.io:80/v2")));
    }
}
