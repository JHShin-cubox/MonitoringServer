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
public class LuggageDTO {
    private Integer passLuggage;
    private Integer openLuggage;
    private Integer totalLuggage;
}
