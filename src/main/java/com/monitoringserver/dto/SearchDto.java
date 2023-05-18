package com.monitoringserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";
    private String searchCategory;
    private String searchText = "";
}
