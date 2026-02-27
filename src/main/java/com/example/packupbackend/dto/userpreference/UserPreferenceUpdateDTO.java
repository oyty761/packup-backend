package com.example.packupbackend.dto.userpreference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceUpdateDTO {

    @Min(value = 1, message = "年龄必须大于0")
    @Max(value = 150, message = "年龄不能超过150岁")
    private Integer age;

    @Pattern(regexp = "^(男|女|其他)$", message = "性别只能是男、女或其他")
    private String gender;

    @Min(value = 1, message = "出行人数至少为1人")
    @Max(value = 20, message = "出行人数不能超过20人")
    private Integer travelCompanions;

    @Min(value = 1, message = "怕冷程度必须在1-5级之间")
    @Max(value = 5, message = "怕冷程度必须在1-5级之间")
    private Integer coldSensitivity;

    @Min(value = 1, message = "怕热程度必须在1-5级之间")
    @Max(value = 5, message = "怕热程度必须在1-5级之间")
    private Integer heatSensitivity;

    @Size(max = 500, message = "健康问题描述不能超过500个字符")
    private String healthIssues;

    @Pattern(regexp = "^(minimal|comprehensive)$", message = "打包风格只能是minimal（精简）或comprehensive（完整）")
    private String packingStyle;
}
