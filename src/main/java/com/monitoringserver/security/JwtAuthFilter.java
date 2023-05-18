package com.monitoringserver.security;

import com.monitoringserver.dto.TokenDTO;
import com.monitoringserver.service.GrantAuthorityService;
import com.monitoringserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final GrantAuthorityService grantAuthorityService;

    @Autowired
    public JwtAuthFilter(TokenProvider tokenProvider, UserService userService, GrantAuthorityService grantAuthorityService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.grantAuthorityService = grantAuthorityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // log.info("filter is running");
        // accessToken, refreshToken 가져오기
        String accessToken = findAccessToken(request).orElse("");
        String refreshToken = findRefreshToken(request).orElse("");
        String userId = "";
//        log.info("accessToken: {}", accessToken);

        try {
            // 두 토큰이 전부 없을 때
            if(accessToken.equals("") && refreshToken.equals("")){
                throw new RuntimeException("no tokens available");
            }

            // accessToken이 만료되고 refreshToken이 존재하면 accessToken을 재발급한다.
            if (accessToken.equals("")) {
                userId = tokenProvider.validateAndGetUserId(refreshToken);
                accessToken = tokenProvider.createAccessToken(userId);
                response.setHeader(HttpHeaders.SET_COOKIE,
                        ResponseCookie.from("accessToken", accessToken)
                                .maxAge(7 * 24 * 60 * 60)
                                .path("/")
                                .httpOnly(true)
                                .build().toString());
            }
            // accessToken이 있으면 검사
            userId = tokenProvider.validateAndGetUserId(accessToken);
//            log.info("user id : " + userId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId, null, AuthorityUtils.NO_AUTHORITIES
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context " + e);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> findRefreshToken(HttpServletRequest request) {
        Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
        if (refreshTokenCookie != null) {
            return Optional.ofNullable(refreshTokenCookie.getValue());
        }
        return Optional.empty();
    }

    public Optional<String> findAccessToken(HttpServletRequest request) {
        Cookie accessTokenCookie = WebUtils.getCookie(request, "accessToken");
        if (accessTokenCookie != null) {
            return Optional.ofNullable(accessTokenCookie.getValue());
        }
        return Optional.empty();
    }


}
