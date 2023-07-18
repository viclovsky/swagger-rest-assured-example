package ru.vicdev.example;

import com.google.inject.Inject;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.vicdev.example.module.ExampleApiModule;
import ru.vicdev.example.swagger.client.ApiClient;

import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.vicdev.example.swagger.client.ResponseSpecBuilders.shouldBeCode;
import static ru.vicdev.example.swagger.client.ResponseSpecBuilders.validatedWith;

@ExtendWith(GuiceExtension.class)
@IncludeModule(ExampleApiModule.class)
public class SimpleJunit5Test {

    @Inject
    private ApiClient api;

    @Test
    public void sizeInventoryTest() {
        Map<String, Integer> inventory = api.store().getInventory().executeAs(validatedWith(shouldBeCode(SC_OK)));
        assertThat(inventory.keySet().size(), greaterThan(0));
    }

    @Test
    public void valueSoldTest() {
        Map<String, Integer> inventory = api.store().getInventory().executeAs(validatedWith(shouldBeCode(SC_OK)));
        assertEquals(inventory.get("sold"), Integer.valueOf(12));
    }

    @Test
    public void keyUnavailableTest() {
        Map<String, Integer> inventory = api.store().getInventory().executeAs(validatedWith(shouldBeCode(SC_OK)));
        assertTrue(inventory.containsKey("unavailable"));
    }
}
