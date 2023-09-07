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
public class DeviceDTO {
    private Long id;
    private String name;
    private Boolean power;
    private String status;
    private Date reg_date;
    private Date modified_date;
    private Integer distribution_count;
    private String type;
}
