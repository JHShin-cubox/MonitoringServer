package com.monitoringserver.mapper;

import com.monitoringserver.dto.OnOffHistoryDTO;
import com.monitoringserver.dto.XrayOnOffHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OnOffHistoryMapper {

    List<OnOffHistoryDTO> getAllHistory(@Param("deviceId") Long deviceId, @Param("type") String type);
}
