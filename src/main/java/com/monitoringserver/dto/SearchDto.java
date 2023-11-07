package com.monitoringserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SearchDto {
    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";
    private String searchRadio = "";
    private String searchCategory;
    private String searchStatus;
    private String searchText = "";
    private String dateStart = "";
    private String dateEnd = "";
    private String pageum;
    private Long offset;
    private Integer pageSize;
    private String baseImage;
}
