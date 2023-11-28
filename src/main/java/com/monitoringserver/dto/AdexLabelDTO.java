package com.monitoringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdexLabelDTO {
    private String labelName;
    private Integer labelCount;
    private Double labelRatio;
}
