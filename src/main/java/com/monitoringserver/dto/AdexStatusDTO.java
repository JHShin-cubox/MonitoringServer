package com.monitoringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdexStatusDTO {
    private Long id;
    private Integer luggageCount;
    private Integer detectionCount;
}
