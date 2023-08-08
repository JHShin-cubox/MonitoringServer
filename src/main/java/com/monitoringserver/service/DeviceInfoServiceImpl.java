package com.monitoringserver.service;

import com.monitoringserver.dto.DeviceDTO;
import com.monitoringserver.dto.SettingDTO;
import com.monitoringserver.mapper.DeviceInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DeviceInfoServiceImpl implements DeviceInfoService {

    private final DeviceInfoMapper pcStatusMapper;

    @Override
    public List<DeviceDTO> getViewerInfo() {
        return pcStatusMapper.getViewerInfo();
    }

    @Override
    public List<DeviceDTO> getXrayInfo() {
        return pcStatusMapper.getXrayInfo();
    }

    @Override
    public Integer getViewerWaitingCount() {
        return pcStatusMapper.getViewerWaitingCount();
    }

    @Override
    public Integer getViewerReadingCount() {
        return pcStatusMapper.getViewerReadingCount();
    }

    @Override
    public Integer getXrayWaitingCount() {
        return pcStatusMapper.getXrayWaitingCount();
    }

    @Override
    public Integer getXrayReadingCount() {
        return pcStatusMapper.getXrayReadingCount();
    }

    @Override
    public List<DeviceDTO> getHistorySel() {
        return pcStatusMapper.getHistorySel();
    }


    @Override
    public List<DeviceDTO> getTrsInfo() {
        return pcStatusMapper.getTrsInfo();
    }

    @Override
    public Integer getTrsWaitingCount() {
        return pcStatusMapper.getTrsWaitingCount();
    }

    @Override
    public Integer getTrsReadingCount() {
        return pcStatusMapper.getTrsReadingCount();
    }

    @Override
    public SettingDTO getSettings() {
        return pcStatusMapper.getSettings();
    }
}
