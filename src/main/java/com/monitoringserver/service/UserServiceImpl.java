package com.monitoringserver.service;

import com.monitoringserver.dto.TokenDTO;
import com.monitoringserver.entity.UserEntity;
import com.monitoringserver.entity.UserRepository;
import com.monitoringserver.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public UserEntity createUser(final UserEntity userEntity) {

        final String userId = userEntity.getUserId();

        if(userRepository.existsByUserId(userId)){
            log.warn("User ID Already Exists");
            throw new RuntimeException("User Id Already Exists");
        }

        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public TokenDTO authenticate(final UserEntity userEntity) {

        // id와 비밀번호 체크
        UserEntity originalUser = userRepository.findByUserId(userEntity.getUserId())
                .orElseThrow(() -> {
                    throw new RuntimeException("Cannot Find User");
        });

        if(originalUser != null && passwordEncoder.matches(userEntity.getUserPassword(),
                                                            originalUser.getUserPassword())){
            // 토큰발급
            String accessToken = tokenProvider.createAccessToken(originalUser.getUserId());
            String refreshToken = tokenProvider.createRefreshToken(originalUser.getUserId());

            TokenDTO tokenDTO = TokenDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            originalUser.setUserToken(refreshToken);
            userRepository.save(originalUser);
            return tokenDTO;

       } else {
            throw new RuntimeException("Password is Wrong");
       }
    }

    @Transactional
    @Override
    public UserEntity save(UserEntity userEntity) {
        if(userEntity == null){
            throw new RuntimeException("database insert or update failure");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new RuntimeException("Cannot Find User");
        });
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.OrderByUserIdxDesc();
    }


    @Override
    public UserEntity getUserByUserIdx(Long userIdx) {
        return userRepository.findByUserIdx(userIdx).orElseThrow(() -> {
            throw new RuntimeException("Cannot Find User");
        });
    }


}
