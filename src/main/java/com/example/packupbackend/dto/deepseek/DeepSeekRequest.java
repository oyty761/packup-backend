package com.example.packupbackend.dto.deepseek;

import lombok.Data;
import java.util.List;

@Data
public class DeepSeekRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer maxTokens;

    @Data
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
