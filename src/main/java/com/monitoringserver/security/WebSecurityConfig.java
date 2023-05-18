package com.monitoringserver.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:5000, " +
                       "http://x-ray.cuboxservice.com, " +
                       "http://xraydata.site:20400," +
                       "http://localhost:3000", allowCredentials = "true")
public class WebSecurityConfig extends WebMvcConfigurationSupport {

    JwtAuthFilter jwtAuthFilter;

    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // csrf 비활성화
            .formLogin().disable()
            .httpBasic().disable() // token을 사용하므로 httpBasic 비활성화
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // token 사용하므로 session 비활성화
            .and()
            .cors()
            .and()
            .logout().permitAll()
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
            .and()
            .authorizeRequests()
            .antMatchers("/api/auth/**", "/api/v2/**", "/api/xray/**", "/login/**", "/static/**", "/logOut/**" , // "/"와 "/api/auth/" 경로는 인증하지않는다
                    "/health", "/swagger-ui/**","/swagger-ui.html/**", "/swagger/**", // swagger
                    "/swagger-resources/**", "/webjars/**", "/v2/api-docs","/favicon.ico" ).permitAll()
            .anyRequest()
            .authenticated().and()
                .formLogin().loginPage("/login");// 그밖의 경로는 인증해야한다.
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.52.5/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://xraydata.site:20400",
                        "http://x-ray.cuboxservice.com",
                        "http://localhost:5000",
                        "http://localhost:3000")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);
    }
}
