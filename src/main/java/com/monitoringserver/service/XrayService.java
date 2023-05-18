package com.monitoringserver.service;

import com.monitoringserver.dto.CountLabelIdDTO;
import com.monitoringserver.dto.XrayStatDTO;
import com.monitoringserver.dto.XrayStatisticDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface XrayService {
    Page<XrayStatisticDTO> getAllStatistics(Optional<Integer> page, Pageable pageable);
    Integer getStatCount();


    XrayStatDTO getOneByLabelId(String labelId);

    List<XrayStatDTO> getAllStatistics2();

    List<CountLabelIdDTO> countAllLabelId();

}
