package com.monitoringserver.service;

import com.monitoringserver.dto.XrayStatisticDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface XrayService {
    Page<XrayStatisticDTO> getAllStatistics(Optional<Integer> page, Pageable pageable);
    Integer getStatCount();
}
