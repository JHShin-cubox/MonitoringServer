package com.monitoringserver.service;

import com.monitoringserver.dto.CountLabelIdDTO;
import com.monitoringserver.dto.XrayStatDTO;
import com.monitoringserver.dto.XrayStatisticDTO;
import com.monitoringserver.entity.XrayRepository;
import com.monitoringserver.mapper.XrayStatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class XrayServiceImpl implements XrayService{


    private final XrayStatisticMapper xrayStatisticMapper;
    private final XrayRepository xrayRepository;

    @Override
    public Page<XrayStatisticDTO> getAllStatistics(Optional<Integer> page, Pageable pageable) {
        List<XrayStatisticDTO> statList = xrayStatisticMapper.getXrayStatistic();
        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()),statList.size());
        return new PageImpl<>(statList.subList(start, end), pageable, statList.size());
    }

    @Override
    public Integer getStatCount() {
        List<XrayStatisticDTO> statList = xrayStatisticMapper.getXrayStatistic();
        return statList.size();
    }

    @Override
    public XrayStatDTO getOneByLabelId(String labelId) {
        return xrayRepository.getOneByLabelId(labelId);
    }

    @Override
    public List<XrayStatDTO> getAllStatistics2() {
        return xrayRepository.getAllStatistics();
    }

    @Override
    public List<CountLabelIdDTO> countAllLabelId() {
        return xrayRepository.countAllLabelId();
    }
}
