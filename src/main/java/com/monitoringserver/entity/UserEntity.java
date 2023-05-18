package com.monitoringserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@DynamicInsert @DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userIdx;

    @Column(length = 100, unique = true, name = "user_id", nullable = false)
    private String userId;

    @Column(length = 100, name = "password")
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;

    @Column(name = "token")
    private String userToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date")
    private Date userCreatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date userUpdatedAt;


    @PrePersist
    protected void onCreate(){
        userCreatedAt = Timestamp.valueOf(LocalDateTime.now());
        userRole = UserRole.USER;
    }
    @PreUpdate
    protected void onUpdate(){
        userUpdatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

}
