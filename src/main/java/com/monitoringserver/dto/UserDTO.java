package com.monitoringserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monitoringserver.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    Long userIdx;
    String userId;
    String userPassword;
    UserRole userRole;
    String userToken;
    Date userCreatedAt;
    Date userUpdatedAt;

    String originalPassword;

}
