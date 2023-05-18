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
public class OnOffHistoryDTO {
    private Long id;
    private String deviceName;
    private Boolean isOn;
    private Date regDate;
    private Long rowNum;
}
