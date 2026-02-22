package com.example.packupbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.packupbackend.mapper")
public class PackupBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PackupBackendApplication.class, args);
    }

}
