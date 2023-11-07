package com.monitoringserver.mapper;

import com.monitoringserver.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface AdexMapper {

    String getLabelName(@Param("labelId") Long labelId);

    void insertAdex(@Param("name")String name, @Param("luggageId")String luggageId);

    AdexDTO getRecentImage();

    String duplicateCheck(@Param("name") String name);

    AdexStatusDTO getAdexStatus();

    List<AdexDTO> getSumImage();

    List<AdexLabelDTO> getTop10();

    List<AdexDTO> getAdexList(SearchDto searchDto);

    Integer getAdexCount();

    List<AdexDTO> getSubImageList(@Param("luggageId")String luggageId);

    String getLastLugId();
}
