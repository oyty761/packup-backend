package com.example.packupbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PackupBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}

@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
