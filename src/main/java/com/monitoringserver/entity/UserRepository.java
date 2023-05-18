package com.monitoringserver.entity;

import com.monitoringserver.dto.UsernamePasswordDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByUserIdx(Long userIdx);
    Boolean existsByUserId(String userId);
    List<UserEntity> OrderByUserIdxDesc();
    Optional<List<UsernamePasswordDTO>> findUserEntityByUserId(String userId);
    Optional<String> findUserPasswordByUserId(String userId);

}
