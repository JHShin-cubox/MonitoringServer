package com.monitoringserver.service;

import com.monitoringserver.dto.DeviceDTO;

import java.util.List;

public interface DeviceInfoService {
   List<DeviceDTO> getViewerInfo();
   List<DeviceDTO> getXrayInfo();

   List<DeviceDTO> getHistorySel();
}
