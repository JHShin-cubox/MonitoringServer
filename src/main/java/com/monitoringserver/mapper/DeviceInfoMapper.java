package com.monitoringserver.mapper;

import com.monitoringserver.dto.DeviceDTO;
import com.monitoringserver.dto.LuggageDTO;
import com.monitoringserver.dto.SettingDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceInfoMapper {

    List<DeviceDTO> getHistorySel();

    List<DeviceDTO> getViewerInfo();
    Integer getViewerWaitingCount();
    Integer getViewerReadingCount();

    List<DeviceDTO> getXrayInfo();
    Integer getXrayWaitingCount();
    Integer getXrayReadingCount();

    List<DeviceDTO> getTrsInfo();
    Integer getTrsWaitingCount();
    Integer getTrsReadingCount();

    SettingDTO getSettings();

}
