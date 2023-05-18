package com.monitoringserver.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@DynamicInsert @DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "xray_analyze_statistics")
public class XrayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_idx")
    private Integer productIdx;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "label_id")
    private String labelId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "score")
    private String score;

    @Column(name = "regdate")
    private LocalDateTime regdate;

}
