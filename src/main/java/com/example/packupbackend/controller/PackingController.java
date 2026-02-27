package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.entity.PackingItem;
import com.example.packupbackend.entity.PackingTemplate;
import com.example.packupbackend.service.PackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.packupbackend.dto.packing.PackingItemCreateDTO;
import com.example.packupbackend.dto.packing.PackingItemUpdateDTO;
import com.example.packupbackend.dto.packing.PackingTemplateCreateDTO;
import com.example.packupbackend.dto.packing.TemplateApplyDTO;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/packing")
@CrossOrigin(origins = "*")
@Slf4j
public class PackingController {

    @Autowired
    private PackingService packingService;

    // --- 清单管理 (Packing List Endpoints) ---

    /**
     * 为指定行程创建新的打包物品。
     */
    @PostMapping("/list")
    public ApiResponse<PackingItem> createPackingItem(@Valid @RequestBody PackingItemCreateDTO dto) {
        PackingItem packingItem = new PackingItem();
        packingItem.setTripId(dto.getTripId());
        packingItem.setName(dto.getName());
        packingItem.setQuantity(dto.getQuantity());
        packingItem.setCategory(dto.getCategory());
        packingItem.setSubCategory(dto.getSubCategory());
        packingItem.setNotes(dto.getNotes());
        packingItem.setSource("manual"); // 手动添加

        log.info("Creating packing item: {}", packingItem);
        return ApiResponse.success(packingService.createPackingItem(packingItem));
    }


    /**
     * 根据ID获取指定打包物品的详细信息。
     */
    @GetMapping("/list/{id}")
    public ApiResponse<PackingItem> getPackingItem(@PathVariable Long id) {
        return packingService.getPackingItem(id)
                .map(item -> ApiResponse.success(item))
                .orElse(ApiResponse.error("Packing item not found with id: " + id));
    }

    /**
     * 获取指定行程的所有打包物品。
     */
    @GetMapping("/list/trip/{tripId}")
    public ApiResponse<List<PackingItem>> getPackingItemsByTrip(@PathVariable Long tripId) {
        return ApiResponse.success(packingService.getPackingItemsByTrip(tripId));
    }

    /**
     * 更新指定打包物品的信息。
     */
    @PutMapping("/list/{id}")
    public ApiResponse<PackingItem> updatePackingItem(@PathVariable Long id,
                                                      @Valid @RequestBody PackingItemUpdateDTO dto) {
        PackingItem item = new PackingItem();
        item.setName(dto.getName());
        item.setQuantity(dto.getQuantity());
        item.setCategory(dto.getCategory());
        item.setSubCategory(dto.getSubCategory());
        item.setNotes(dto.getNotes());
        item.setIsPacked(dto.getIsPacked());

        return ApiResponse.success(packingService.updatePackingItem(id, item));
    }


    /**
     * 删除指定的打包物品。
     */
    @DeleteMapping("/list/{id}")
    public ApiResponse<Void> deletePackingItem(@PathVariable Long id) {
        packingService.deletePackingItem(id);
        return ApiResponse.success("打包物品删除成功");
    }

    // --- 模板管理 (Template Endpoints) ---

    /**
     * 将指定行程的打包物品保存为一个新模板。
     */
    @PostMapping("/template")
    public ApiResponse<PackingTemplate> saveAsTemplate(@Valid @RequestBody PackingTemplateCreateDTO dto) {
        return ApiResponse.success(packingService.saveAsTemplate(
                dto.getTemplateName(),
                dto.getDescription(),
                dto.getTripId()
        ));
    }


    /**
     * 应用一个模板来为指定行程创建打包物品。
     */
    @PostMapping("/template/apply")
    public ApiResponse<List<PackingItem>> applyTemplate(@Valid @RequestBody TemplateApplyDTO dto) {
        return ApiResponse.success(packingService.applyTemplate(dto.getTemplateId(), dto.getTripId()));
    }


    /**
     * 获取所有已保存的模板列表。
     */
    @GetMapping("/templates")
    public ApiResponse<List<PackingTemplate>> getAllTemplates() {
        return ApiResponse.success(packingService.getAllTemplates());
    }
}
