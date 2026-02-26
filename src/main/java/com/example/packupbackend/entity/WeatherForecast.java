package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
/**
 * 天气预报实体类
 * 对应数据库表: weather_forecast
 * 
 * 存储行程目的地的天气预报信息，用于智能打包建议。
 * 通过调用和风天气API获取未来几天的天气数据。
 * 
 * 主要功能:
 * - 天气基础数据存储（温度、降水、湿度等）
 * - 紫外线指数记录
 * - 天气描述信息
 * - 与行程表多对一关联
 * - 支持按城市和日期查询
 */
public class WeatherForecast {
    private Long id; // 天气预报唯一标识符，主键
    private Long tripId; // 关联的行程ID，外键
    private LocalDate forecastDate; // 预报日期
    private String city; // 城市名称
    private Float minTemp; // 最低温度
    private Float maxTemp; // 最高温度
    private Float precipitation; // 降水量(mm)
    private Integer humidity; // 湿度百分比
    private Integer uvIndex; // 紫外线指数
    private String weatherDesc; // 天气描述
    private LocalDateTime fetchTime; // 数据获取时间
}