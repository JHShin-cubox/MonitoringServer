package com.monitoringserver.service;

import com.monitoringserver.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class GrantAuthorityService {

    public Collection<GrantedAuthority> authorities(UserEntity userEntity){
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(userEntity.getUserRole().toString()));
        return authorities;

    }
}
