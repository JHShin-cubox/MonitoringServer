package com.monitoringserver.service;

import com.monitoringserver.dto.OnOffHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OnOffHistoryService {
   Page<OnOffHistoryDTO> getAllHistory(Optional<Integer> page, Pageable pageable, Long deviceId, String type);

   Integer getAllCount(Long deviceId, String type);



}
