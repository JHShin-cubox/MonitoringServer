package com.monitoringserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@DynamicInsert @DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "status_history")
public class StatusHistoryEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long historyIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pc_idx", nullable = false)
    PcStatusEntity pcIdx;

    @Column(name = "luggage_id")
    String luggageId;

    @Column(name = "time")
    LocalDateTime time;

    @Column(name = "connected")
    boolean connected;


}
