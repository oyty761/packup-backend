package com.example.packupbackend.service;

import com.example.packupbackend.entity.Trip;

public interface WeatherBasedPackingService {
    //根据天气情况生成携带的物品
    void generateItemsFromWeather(Trip trip);
}
