/*==================================================================
프로젝트명 : 판독 웹뷰어
작성지 : 신정호
작성일 : 2023년 11월 22일
용도 : 판독 웹뷰어 위해물품 라벨 정보 교환 객체
==================================================================*/

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
