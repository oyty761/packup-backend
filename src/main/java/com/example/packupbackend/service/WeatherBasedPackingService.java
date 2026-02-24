package com.example.packupbackend.service;

import com.example.packupbackend.config.WeatherApiConfig;
import com.example.packupbackend.entity.PackingItem;
import com.example.packupbackend.entity.Trip;
import com.example.packupbackend.entity.TripDestination;
import com.example.packupbackend.entity.WeatherForecast;
import com.example.packupbackend.mapper.PackingItemMapper;
import com.example.packupbackend.mapper.TripDestinationMapper;
import com.example.packupbackend.mapper.WeatherForecastMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class WeatherBasedPackingService {

    private final WeatherApiConfig weatherApiConfig;
    private final RestTemplate restTemplate;
    private final TripDestinationMapper tripDestinationMapper;
    private final WeatherForecastMapper weatherForecastMapper;
    private final PackingItemMapper packingItemMapper;

    public WeatherBasedPackingService(WeatherApiConfig weatherApiConfig,
                                      RestTemplate restTemplate,
                                      TripDestinationMapper tripDestinationMapper,
                                      WeatherForecastMapper weatherForecastMapper,
                                      PackingItemMapper packingItemMapper) {
        this.weatherApiConfig = weatherApiConfig;
        this.restTemplate = restTemplate;
        this.tripDestinationMapper = tripDestinationMapper;
        this.weatherForecastMapper = weatherForecastMapper;
        this.packingItemMapper = packingItemMapper;
    }

    /**
     * 根据行程的目的地天气，生成/更新行李物品
     */
    @Transactional
    public void generateItemsFromWeather(Trip trip) {
        // 1. 获取行程的所有目的地（trip_destination表）
        List<TripDestination> destinations = tripDestinationMapper.findByTripId(trip.getId());
        if (destinations.isEmpty()) {
            log.info("行程 {} 没有目的地，跳过天气物品生成", trip.getId());
            return;
        }

        // 2. 为每个目的地获取天气预报并保存
        List<WeatherForecast> allForecasts = new ArrayList<>();
        for (TripDestination dest : destinations) {
            List<WeatherForecast> forecasts = fetchAndSaveWeatherForecast(dest, trip.getId());
            allForecasts.addAll(forecasts);
        }

        // 3. 基于所有天气预报分析，生成推荐物品
        List<PackingItem> suggestedItems = analyzeWeatherAndSuggestItems(allForecasts, trip);

        // 4. 保存或更新物品到数据库（避免重复添加）
        for (PackingItem item : suggestedItems) {
            PackingItem existing = packingItemMapper.findByTripIdAndNameAndSource(trip.getId(), item.getName(), "weather");
            if (existing == null) {
                item.setTrip(trip);          // 建立与Trip的关联
                item.setSource("weather");
                item.setIsPacked(false);
                packingItemMapper.insert(item);
            } else {
                log.debug("物品 {} 已存在，跳过", item.getName());
            }
        }
    }

    /**
     * 获取指定目的地在行程日期范围内的天气预报，并保存到数据库
     */
    private List<WeatherForecast> fetchAndSaveWeatherForecast(TripDestination destination, Long tripId) {
        String cityName = destination.getCityName();
        LocalDate start = destination.getArrivalDate();
        LocalDate end = destination.getDepartureDate();
        if (start == null || end == null) {
            log.warn("目的地 {} 日期不完整，跳过天气获取", cityName);
            return Collections.emptyList();
        }

        String locationId = getLocationId(cityName);
        if (locationId == null) {
            log.error("无法获取城市 {} 的ID", cityName);
            return Collections.emptyList();
        }

        List<WeatherForecast> forecasts = fetchWeatherForecasts(locationId, cityName, start, end, tripId);

        // 保存到数据库（weather_forecast表）
        for (WeatherForecast forecast : forecasts) {
            WeatherForecast existing = weatherForecastMapper.findByTripIdAndCityAndDate(tripId, cityName, forecast.getForecastDate());
            if (existing == null) {
                weatherForecastMapper.insert(forecast);
            } else {
                forecast.setId(existing.getId());
                weatherForecastMapper.update(forecast);
            }
        }
        return forecasts;
    }

    /**
     * 调用和风城市搜索API获取locationId
     */
    private String getLocationId(String cityName) {
        String url = UriComponentsBuilder.fromHttpUrl(weatherApiConfig.getGeoUrl())
                .queryParam("location", cityName)
                .queryParam("key", weatherApiConfig.getKey())
                .build()
                .toUriString();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && "200".equals(response.get("code"))) {
     
