package com.example.packupbackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiConfig {
    private String key;
    private String geoUrl;
    private String weatherUrl;
    private String indicesUrl;
}
