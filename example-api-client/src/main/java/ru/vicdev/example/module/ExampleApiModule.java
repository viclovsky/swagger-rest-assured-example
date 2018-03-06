package ru.vicdev.example.module;

import com.google.inject.AbstractModule;
import ru.vicdev.example.provider.ExampleApiProvider;
import ru.vicdev.example.swagger.client.ApiClient;

public class ExampleApiModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ApiClient.class).toProvider(ExampleApiProvider.class);
    }
}
