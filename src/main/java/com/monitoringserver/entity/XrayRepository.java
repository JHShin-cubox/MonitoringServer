package com.monitoringserver.entity;

import com.monitoringserver.dto.CountLabelIdDTO;
import com.monitoringserver.dto.XrayStatDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XrayRepository extends JpaRepository<XrayEntity, Integer> {
    XrayEntity findByIp(String ip);

    @Query(value = "select label_id, " +
            "count(*) as amount " +
            "from xray_analyze_statistics " +
            "where label_id = :label_id " +
            "group by label_id", nativeQuery = true)
    XrayStatDTO getOneByLabelId(@Param("label_id") String labelId);

    @Query(value = "select " +
            "label_name, " +
            "label_id, " +
            "xray_name as name," +
            "count(*) as amount, " +
            "score," +
            "round(100. * count(*) / sum(count(*)) over (), 2) as percentage " +
            "from xray_analyze_statistics " +
            "group by label_id " +
            "order by percentage desc", nativeQuery = true)
    List<XrayStatDTO> getAllStatistics();

    @Query(value = "select " +
            "label_id, " +
            "count(*) as amount, " +
            "round(100. * count(*) / sum(count(*)) over (), 2) as percentage " +
            "from xray_analyze_statistics " +
            "group by label_id ", nativeQuery = true)
    List<CountLabelIdDTO> countAllLabelId();
}

