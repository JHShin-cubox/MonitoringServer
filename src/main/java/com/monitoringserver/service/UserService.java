package com.monitoringserver.service;

import com.monitoringserver.dto.TokenDTO;
import com.monitoringserver.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(final UserEntity userEntity);
    TokenDTO authenticate(final UserEntity userEntity);
    UserEntity save(final UserEntity userEntity);
    UserEntity getUserByUserId(String userId);
    List<UserEntity> getAllUsers();

    UserEntity getUserByUserIdx(Long userIdx);
}
