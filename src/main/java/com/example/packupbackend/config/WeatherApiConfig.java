package com.example.packupbackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiConfig {
    private String key;//天气API访问密钥
    private String geoUrl;//地理位置API地址
    private String weatherUrl;//天气预报API地址
    private String indicesUrl;//生活指数API地址
}
