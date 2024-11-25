package com.example.dx_kiosk.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("DX_AdminDashBoard API")
                .description("DX_AdminDashBoard API Documentation")
                .version("1.0");
    }

    public SwaggerConfig(MappingJackson2HttpMessageConverter converter) {
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }

}