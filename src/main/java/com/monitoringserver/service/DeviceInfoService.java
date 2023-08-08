package com.monitoringserver.service;

import com.monitoringserver.dto.DeviceDTO;
import com.monitoringserver.dto.SettingDTO;

import java.util.List;

public interface DeviceInfoService {
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
