package com.example.packupbackend.service.impl;

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
public class WeatherBasedPackingServiceImpl {

    private final WeatherApiConfig weatherApiConfig;
    private final RestTemplate restTemplate;
    private final TripDestinationMapper tripDestinationMapper;
    private final WeatherForecastMapper weatherForecastMapper;
    private final PackingItemMapper packingItemMapper;

    public WeatherBasedPackingServiceImpl(WeatherApiConfig weatherApiConfig,
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
                List<Map<String, Object>> locationList = (List<Map<String, Object>>) response.get("location");
                if (locationList != null && !locationList.isEmpty()) {
                    return (String) locationList.get(0).get("id");
                }
            }
        } catch (Exception e) {
            log.error("获取城市ID失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 调用和风7天预报API获取指定日期范围内的天气
     */
    private List<WeatherForecast> fetchWeatherForecasts(String locationId, String cityName, LocalDate start, LocalDate end, Long tripId) {
        String url = UriComponentsBuilder.fromHttpUrl(weatherApiConfig.getWeatherUrl())
                .queryParam("location", locationId)
                .queryParam("key", weatherApiConfig.getKey())
                .build()
                .toUriString();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && "200".equals(response.get("code"))) {
                List<Map<String, Object>> dailyList = (List<Map<String, Object>>) response.get("daily");
                if (dailyList != null) {
                    List<WeatherForecast> result = new ArrayList<>();
                    for (Map<String, Object> daily : dailyList) {
                        String fxDate = (String) daily.get("fxDate");
                        LocalDate date = LocalDate.parse(fxDate);
                        if (!date.isBefore(start) && !date.isAfter(end)) {
                            WeatherForecast forecast = new WeatherForecast();
                            forecast.setTripId(tripId);
                            forecast.setForecastDate(date);
                            forecast.setCity(cityName);
                            forecast.setMinTemp(parseFloat(daily.get("tempMin")));
                            forecast.setMaxTemp(parseFloat(daily.get("tempMax")));
                            forecast.setPrecipitation(parseFloat(daily.get("precip")));
                            forecast.setHumidity(parseInt(daily.get("humidity")));
                            forecast.setWeatherDesc((String) daily.get("textDay"));
                            forecast.setFetchTime(LocalDateTime.now());

                            // 可选：获取紫外线指数（需额外调用生活指数API）
                            // forecast.setUvIndex(fetchUVIndex(locationId, date));

                            result.add(forecast);
                        }
                    }
                    return result;
                }
            }
        } catch (Exception e) {
            log.error("获取天气预报失败: {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    // 辅助转换方法
    private Float parseFloat(Object obj) {
        if (obj == null) return null;
        try {
            return Float.parseFloat(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInt(Object obj) {
        if (obj == null) return null;
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    
    /**
     * 分析天气数据，生成建议的物品列表
     */
    private List<PackingItem> analyzeWeatherAndSuggestItems(List<WeatherForecast> forecasts, Trip trip) {
        Set<String> itemNames = new HashSet<>();
        List<PackingItem> items = new ArrayList<>();

        boolean hasRain = forecasts.stream().anyMatch(f -> f.getWeatherDesc() != null && f.getWeatherDesc().contains("雨"));
        boolean hasSnow = forecasts.stream().anyMatch(f -> f.getWeatherDesc() != null && f.getWeatherDesc().contains("雪"));
        boolean highTemp = forecasts.stream().anyMatch(f -> f.getMaxTemp() != null && f.getMaxTemp() > 28);
        boolean lowTemp = forecasts.stream().anyMatch(f -> f.getMinTemp() != null && f.getMinTemp() < 5);
        boolean strongUV = forecasts.stream().anyMatch(f -> f.getUvIndex() != null && f.getUvIndex() >= 5);

        if (hasRain && !itemNames.contains("雨伞")) {
            items.add(createPackingItem("雨伞", "其他物品", 1, "建议携带雨伞，以防下雨", trip));
            itemNames.add("雨伞");
        }
        if (hasSnow && !itemNames.contains("雪地靴")) {
            items.add(createPackingItem("雪地靴", "衣物鞋包", 1, "下雪天气，建议穿防水保暖的雪地靴", trip));
            itemNames.add("雪地靴");
        }
        if (highTemp && !itemNames.contains("防晒霜")) {
            items.add(createPackingItem("防晒霜", "洗漱护肤", 1, "气温较高，紫外线可能较强，建议涂抹防晒霜", trip));
            itemNames.add("防晒霜");
        }
        if (lowTemp && !itemNames.contains("羽绒服")) {
            items.add(createPackingItem("羽绒服", "衣物鞋包", 1, "天气寒冷，建议携带羽绒服", trip));
            itemNames.add("羽绒服");
        }
        if (strongUV && !itemNames.contains("太阳镜")) {
            items.add(createPackingItem("太阳镜", "其他物品", 1, "紫外线强，建议佩戴太阳镜保护眼睛", trip));
            itemNames.add("太阳镜");
        }
        // 可根据需要添加更多规则，如湿度大建议带除湿袋等

        return items;
    }

    private PackingItem createPackingItem(String name, String category, int quantity, String notes, Trip trip) {
        PackingItem item = new PackingItem();
        item.setName(name);
        item.setCategory(category);
        item.setQuantity(quantity);
        item.setNotes(notes);
        item.setTrip(trip);       // 关联Trip对象，插入时会自动设置trip_id
        return item;
    }
}
