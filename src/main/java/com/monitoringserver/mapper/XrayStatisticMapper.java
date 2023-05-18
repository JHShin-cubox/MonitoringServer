package com.monitoringserver.mapper;

import com.monitoringserver.dto.OnOffHistoryDTO;
import com.monitoringserver.dto.XrayStatisticDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface XrayStatisticMapper {

    List<XrayStatisticDTO> getXrayStatistic();
}
