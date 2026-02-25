package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.entity.PackingItem;
import com.example.packupbackend.model.PackingList;
import com.example.packupbackend.entity.PackingTemplate;
import com.example.packupbackend.service.PackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/packing")
@CrossOrigin(origins = "*")
public class PackingController {

    @Autowired
    private PackingService packingService;

    // --- 清单管理 (Packing List Endpoints) ---

    /**
     * 创建一个新的打包清单。
     */
    @PostMapping("/list")
    public ApiResponse<PackingList> createPackingList(@RequestBody PackingList packingList) {
        return ApiResponse.success(packingService.createPackingList(packingList));
    }

    /**
     * 根据ID获取指定打包清单的详细信息。
     */
    @GetMapping("/list/{id}")
    public ApiResponse<PackingList> getPackingList(@PathVariable Long id) {
        return packingService.getPackingList(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error("Packing list not found with id: " + id));
    }

    /**
     * 向指定的打包清单中添加一个新物品。
     */
    @PostMapping("/list/{id}/item")
    public ApiResponse<PackingList> addItemToPackingList(@PathVariable Long id, @RequestBody PackingItem item) {
        return ApiResponse.success(packingService.addItemToPackingList(id, item));
    }

    /**
     * 更新指定打包清单中某个物品的信息。
     */
    @PutMapping("/list/{listId}/item/{itemId}")
    public ApiResponse<PackingList> updateItemInPackingList(@PathVariable Long listId, @PathVariable Long itemId, @RequestBody PackingItem item) {
        return ApiResponse.success(packingService.updateItemInPackingList(listId, itemId, item));
    }

    /**
     * 从指定的打包清单中删除一个物品。
     */
    @DeleteMapping("/list/{listId}/item/{itemId}")
    public ApiResponse<PackingList> deleteItemFromPackingList(@PathVariable Long listId, @PathVariable Long itemId) {
        return ApiResponse.success(packingService.deleteItemFromPackingList(listId, itemId));
    }

    // --- 模板管理 (Template Endpoints) ---

    /**
     * 将一个现有的打包清单保存为一个新模板。
     */
    @PostMapping("/template")
    public ApiResponse<PackingTemplate> saveAsTemplate(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String category = payload.get("category");
        Long packingListId = Long.parseLong(payload.get("packingListId"));
        return ApiResponse.success(packingService.saveAsTemplate(name, category, packingListId));
    }

    /**
     * 应用一个模板来创建一个新的打包清单。。
     */
    @PostMapping("/template/apply")
    public ApiResponse<PackingList> applyTemplate(@RequestBody Map<String, String> payload) {
        Long templateId = Long.parseLong(payload.get("templateId"));
        String tripName = payload.get("tripName");
        return ApiResponse.success(packingService.applyTemplate(templateId, tripName));
    }

    /**
     * 获取所有已保存的模板列表。
     */
    @GetMapping("/templates")
    public ApiResponse<List<PackingTemplate>> getAllTemplates() {
        return ApiResponse.success(packingService.getAllTemplates());
    }
}
