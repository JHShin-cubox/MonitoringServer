package com.monitoringserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface XrayStatDTO {
    String getLabel_name();
    String getLabel_id();
    String getName();
    String getScore();
    int getAmount();
    Double getPercentage();
}
