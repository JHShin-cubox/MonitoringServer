/*==================================================================
프로젝트명 : 판독 웹뷰어
작성지 : 신정호
작성일 : 2023년 11월 22일
용도 : 판독 웹뷰어 데이터 교환 객체
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
public class AdexDTO {
    private Long id;
    private String name;
    private String luggageId;
    private String regDate;
}
