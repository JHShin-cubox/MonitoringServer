package com.monitoringserver.mapper;

import com.monitoringserver.dto.DeviceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceInfoMapper {
    List<DeviceDTO> getXrayInfo();
    List<DeviceDTO> getViewerInfo();
    List<DeviceDTO> getHistorySel();
}
