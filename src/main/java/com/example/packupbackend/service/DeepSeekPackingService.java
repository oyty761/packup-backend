package com.example.packupbackend.service;

import com.example.packupbackend.config.DeepSeekConfig;
import com.example.packupbackend.dto.deepseek.DeepSeekRequest;
import com.example.packupbackend.dto.deepseek.DeepSeekResponse;
import com.example.packupbackend.entity.PackingItem;
import com.example.packupbackend.entity.Trip;
import com.example.packupbackend.entity.TripDestination;
import com.example.packupbackend.entity.WeatherForecast;
import com.example.packupbackend.mapper.PackingItemMapper;
import com.example.packupbackend.mapper.TripDestinationMapper;
import com.example.packupbackend.service.WeatherForecastService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeepSeekPackingService {

    private final DeepSeekConfig deepSeekConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // Spring Boot 自动注入
    private final TripDestinationMapper tripDestinationMapper;
    private final WeatherForecastService weatherForecastService;
    private final PackingItemMapper packingItemMapper;

    /**
     * 根据行程生成智能行李物品（调用 DeepSeek）
     * @return 生成的物品列表
     */
    @Transactional
    public List<PackingItem> generateItemsForTrip(Trip trip) {
        List<PackingItem> createdItems = new ArrayList<>();
        
        // 1. 获取行程相关数据
        List<TripDestination> destinations = tripDestinationMapper.findByTripId(trip.getId());
        if (destinations.isEmpty()) {
            log.warn("行程 {} 没有目的地，无法生成 AI 物品", trip.getId());
            return createdItems;
        }

        // 2. 获取每个目的地的天气（已有接口）
        List<WeatherForecast> allWeather = new ArrayList<>();
        for (TripDestination dest : destinations) {
            List<WeatherForecast> forecasts = weatherForecastService.getForecastsByTripIdAndDateRange(
                    trip.getId(), dest.getArrivalDate(), dest.getDepartureDate());
            allWeather.addAll(forecasts);
        }

        // 3. 构建提示词
        String prompt = buildPrompt(trip, destinations, allWeather);

        // 4. 调用 DeepSeek API
        String aiResponse = callDeepSeek(prompt);
        if (aiResponse == null) {
            log.error("DeepSeek API 调用失败，行程 {}", trip.getId());
            return createdItems;
        }

        // 5. 解析响应为物品列表
        List<Map<String, Object>> itemMaps = parseResponse(aiResponse);
        if (itemMaps == null || itemMaps.isEmpty()) {
            log.warn("DeepSeek 未返回有效物品，行程 {}", trip.getId());
            return createdItems;
        }

        // 6. 转换为 PackingItem 并保存（避免重复）
        for (Map<String, Object> itemMap : itemMaps) {
            try {
                String name = (String) itemMap.get("name");
                String category = (String) itemMap.get("category");
                Object quantityObj = itemMap.get("quantity");
                String notes = (String) itemMap.get("notes");
                        
                // 详细日志，便于调试
                log.debug("准备解析物品：name={}, category={}, quantity={}, notes={}", 
                         name, category, quantityObj, notes);
        
                // 检查必要字段
                if (name == null || name.trim().isEmpty()) {
                    log.error("物品名称为空，跳过此物品：{}", itemMap);
                    continue;
                }
        
                // 安全转换 quantity
                Integer quantity = 1;
                if (quantityObj != null) {
                    if (quantityObj instanceof Number) {
                        quantity = ((Number) quantityObj).intValue();
                    } else if (quantityObj instanceof String) {
                        try {
                            quantity = Integer.parseInt((String) quantityObj);
                        } catch (NumberFormatException e) {
                            log.warn("数量格式错误，使用默认值 1: {}", quantityObj);
                        }
                    }
                }
        
                // 检查是否已存在相同名称且来源为 "ai" 的物品
                PackingItem existing = packingItemMapper.findByTripIdAndNameAndSource(trip.getId(), name, "ai");
                if (existing == null) {
                    PackingItem item = new PackingItem();
                    item.setName(name.trim());
                    item.setCategory(category != null ? category.trim() : "其他物品");
                    item.setQuantity(quantity);
                    item.setNotes(notes != null ? notes.trim() : "AI 智能推荐");
                    item.setSource("ai");
                    item.setIsPacked(false);
                    item.setTripId(trip.getId());
                    
                    log.debug("准备插入物品：name={}, category={}, quantity={}, notes={}, tripId={}", 
                             item.getName(), item.getCategory(), item.getQuantity(), item.getNotes(), item.getTripId());
                    packingItemMapper.insert(item);
                    createdItems.add(item);
                    log.info("成功插入 AI 物品：{} (tripId={})", name, trip.getId());
                } else {
                    log.debug("物品 {} 已存在，跳过", name);
                }
            } catch (Exception e) {
                log.error("解析物品失败：itemMap={}, 错误类型：{}, 错误消息：{}", 
                         itemMap, e.getClass().getSimpleName(), e.getMessage(), e);
            }
        }
        
        return createdItems;
    }

    /**
     * 构建发送给 DeepSeek 的提示词
     */
    private String buildPrompt(Trip trip, List<TripDestination> destinations, List<WeatherForecast> weather) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一个智能行李整理助手。请根据以下行程信息，推荐一份行李物品清单。\n");
        sb.append("行程名称：").append(trip.getTripName()).append("\n");
        sb.append("日期：").append(trip.getStartDate()).append(" 至 ").append(trip.getEndDate()).append("\n");
        sb.append("目的地：\n");
        for (TripDestination d : destinations) {
            sb.append("- ").append(d.getCityName());
            if (d.getPoiName() != null) {
                sb.append(" (").append(d.getPoiName()).append(")");
            }
            sb.append(" 到达：").append(d.getArrivalDate())
              .append(" 离开：").append(d.getDepartureDate()).append("\n");
        }
        if (!weather.isEmpty()) {
            sb.append("天气预报：\n");
            for (WeatherForecast w : weather) {
                sb.append("- ").append(w.getForecastDate())
                  .append(": ").append(w.getWeatherDesc())
                  .append(", 温度 ").append(w.getMinTemp()).append("~").append(w.getMaxTemp()).append("℃")
                  .append(", 降水 ").append(w.getPrecipitation()).append("mm")
                  .append(", 湿度 ").append(w.getHumidity()).append("%")
                  .append(", 紫外线 ").append(w.getUvIndex()).append("\n");
            }
        }
        sb.append("\n请以 JSON 数组格式返回推荐物品，每个物品包含字段：name（名称）、category（类别，可选）、quantity（数量，默认1）、notes（备注，可选）。\n");
        sb.append("示例：\n");
        sb.append("[{\"name\":\"护照\", \"category\":\"重要文件\", \"quantity\":1, \"notes\":\"国际旅行必备\"}, {\"name\":\"雨伞\", \"category\":\"其他物品\", \"quantity\":1, \"notes\":\"当地可能有雨\"}]\n");
        sb.append("请直接返回 JSON 数组，不要包含其他文字。");
        return sb.toString();
    }

    /**
     * 调用 DeepSeek API
     */
    private String callDeepSeek(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(deepSeekConfig.getKey());

        List<DeepSeekRequest.Message> messages = new ArrayList<>();
        messages.add(new DeepSeekRequest.Message("system", "你是一个智能行李助手，根据行程信息推荐行李物品。"));
        messages.add(new DeepSeekRequest.Message("user", prompt));

        DeepSeekRequest request = new DeepSeekRequest();
        request.setModel(deepSeekConfig.getModel());
        request.setMessages(messages);
        request.setTemperature(0.7);
        request.setMaxTokens(800);

        HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        try {
            DeepSeekResponse response = restTemplate.postForObject(
                    deepSeekConfig.getUrl(),
                    entity,
                    DeepSeekResponse.class
            );
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            }
        } catch (RestClientException e) {
            log.error("调用 DeepSeek API 异常", e);
        }
        return null;
    }

    /**
     * 解析 API 返回的 JSON 字符串为物品列表
     */
    private List<Map<String, Object>> parseResponse(String content) {
        try {
            // 尝试直接解析为 List<Map>
            return objectMapper.readValue(content, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            log.error("解析 DeepSeek 响应失败，内容：{}", content, e);
            return null;
        }
    }
}
