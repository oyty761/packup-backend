package com.example.packupbackend.dto.packing;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
public class PackingTemplateCreateDTO {

    @NotBlank(message = "模板名称不能为空")
    private String templateName;

    private String description;

    @NotNull(message = "行程ID不能为空")
    private Long tripId;

    // getters and setters
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }
}
