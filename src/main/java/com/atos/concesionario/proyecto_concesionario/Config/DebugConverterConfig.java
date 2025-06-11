package com.atos.concesionario.proyecto_concesionario.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class DebugConverterConfig implements WebMvcConfigurer {

    private final List<HttpMessageConverter<?>> converters;

    public DebugConverterConfig(List<HttpMessageConverter<?>> converters) {
        this.converters = converters;
    }

    @PostConstruct
    public void init() {
        System.out.println("Converters registrados en Spring:");
        converters.forEach(converter -> System.out.println(" - " + converter.getClass().getName()));
    }
}