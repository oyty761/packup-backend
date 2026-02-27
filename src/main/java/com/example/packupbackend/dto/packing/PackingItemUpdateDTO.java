package com.example.packupbackend.dto.packing;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class PackingItemUpdateDTO {

    @NotBlank(message = "物品名称不能为空")
    private String name;

    @Positive(message = "数量必须大于0")
    private Integer quantity;

    private String category;

    private String subCategory;

    private String notes;

    private Boolean isPacked;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getIsPacked() {
        return isPacked;
    }

    public void setIsPacked(Boolean isPacked) {
        this.isPacked = isPacked;
    }
}


