package com.monitoringserver.controller;

import com.monitoringserver.dto.RequestAuthDTO;
import com.monitoringserver.dto.ResponseDTO;
import com.monitoringserver.dto.TokenDTO;
import com.monitoringserver.dto.UserDTO;
import com.monitoringserver.entity.UserEntity;
import com.monitoringserver.security.TokenProvider;
import com.monitoringserver.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;
    private TokenProvider tokenProvider;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService,
                          TokenProvider tokenProvider,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;

    }


    /**
     * 회원가입
     */
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<?> createUser(@RequestBody RequestAuthDTO authDTO) {

        try {
            UserEntity user = UserEntity.builder()
                    .userId(authDTO.getUserId())
                    .userPassword(passwordEncoder.encode(authDTO.getUserPassword()))
                    .build();

            UserEntity registeredUser = userService.createUser(user);
            // 회원가입이 성공하면 아이디를 리턴한다
            UserDTO responseUserDTO = UserDTO.builder()
                    .userId(registeredUser.getUserId())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDTO);

        } catch (Exception e) {
            // 실패하면 에러메시지를 리턴한다
            ResponseDTO response = ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    /**
     * 로그인
     */
    @PostMapping("/signin")
    @ApiOperation(value = "로그인", response = ResponseDTO.class)
    public ResponseEntity<?> signInUser(@RequestBody RequestAuthDTO userDTO) {
        try {
            UserEntity user = UserEntity.builder()
                    .userId(userDTO.getUserId())
                    .userPassword(userDTO.getUserPassword())
                    .build();

            TokenDTO tokens = userService.authenticate(user);

            // 쿠키에 토큰을 담아 response에 전달
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE,
                            ResponseCookie.from("refreshToken", tokens.getRefreshToken())
                                    .maxAge(7 * 24 * 60 * 60)
                                    .path("/")
                                    .httpOnly(true)
                                    .build().toString())
                    .header(HttpHeaders.SET_COOKIE,
                            ResponseCookie.from("accessToken", tokens.getAccessToken())
                                    .maxAge(7 * 24 * 60 * 60)
                                    .path("/")
                                    .httpOnly(true)
                                    .build().toString())
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.OK.value())
                            .data(List.of(userDTO.getUserId()))
                            .build());

        } catch (Exception e) {
            // 실패하면 에러메시지를 리턴
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseDTO.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.NOT_FOUND.value())
                            .data(List.of())
                            .build());

        }
    }

    /**
     * 로그아웃
     */
    @GetMapping("/signout")
    @ApiOperation(value = "로그아웃")
    public ResponseEntity<?> signOutUser() {
        // maxAge를 0으로 만들어 쿠키에 저장된 토큰 삭제
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, ResponseCookie.from("accessToken", "")
                        .path("/")
                        .maxAge(0)
                        .build().toString())
                .header(HttpHeaders.SET_COOKIE, ResponseCookie.from("refreshToken", "")
                        .path("/")
                        .maxAge(0)
                        .build().toString())
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .data(List.of())
                        .build());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("/login"));
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    /*
    /**
     * 토큰 재발급
     *
    @PostMapping("/refresh")
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<?> refreshToken(@RequestBody RequestAuthDTO authDTO) {
        UserEntity entity = userService.getUserByUserId(authDTO.getUserId());
        // TODO 쿠키에서 refreshtoken을 가져와서 값이 없으면 refreshtoken과 accesstoken을 재발급
        String token = tokenProvider.createAccessToken(authDTO.getUserId());
        entity.setUserToken(token);
        userService.save(entity);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, ResponseCookie.from("accessToken", "")
                        .domain(domain)
                        .path("/")
                        .maxAge(0)
                        .build().toString())
                .header(HttpHeaders.SET_COOKIE,
                        ResponseCookie.from("refreshToken", token)
                                .maxAge(7 * 24 * 60 * 60)
                                .domain(domain)
                                .path("/")
                                .httpOnly(true)
                                .build().toString())
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .data(List.of())
                        .build());
    }*/

}

