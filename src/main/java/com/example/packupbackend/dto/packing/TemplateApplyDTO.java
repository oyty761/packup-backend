package com.example.packupbackend.dto.packing;

import javax.validation.constraints.NotNull;

public class TemplateApplyDTO {

    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    @NotNull(message = "行程ID不能为空")
    private Long tripId;

    // getters and setters
    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }
}

