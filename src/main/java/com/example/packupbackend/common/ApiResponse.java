package com.example.packupbackend.common;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;//请求是否成功
    private String message;//响应消息
    private T data;
    private long timestamp;//响应生成时间

    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }//自动设置当前时间

    public ApiResponse(boolean success, String message) {
        this();//进行初始化
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {//初始化所有字段
        this(success, message);
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "操作成功", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}