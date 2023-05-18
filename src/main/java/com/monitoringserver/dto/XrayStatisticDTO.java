package com.monitoringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class XrayStatisticDTO {
    private String xrayName;
    private String labelId;
    private String labelName;
    private Integer amount;
    private String score;
    private String percentage;
}
