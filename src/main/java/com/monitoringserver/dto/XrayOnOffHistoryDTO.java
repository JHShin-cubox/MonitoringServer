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
public class XrayOnOffHistoryDTO {
    private Long id;
    private Long xray_id;
    private Boolean is_on;
    private Date reg_date;
}
