package com.monitoringserver.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storg/**")
                .addResourceLocations("classpath:/storg/");
        registry.addResourceHandler("/adex_image/**")
//                .addResourceLocations("file:///C:/project/sshtest/");
//                .addResourceLocations("file:///home/ubuntu/Pictures/");
//                .addResourceLocations("file:///home/cubox/Pictures/");
                .addResourceLocations("file:///home/cubox/image/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://172.16.150.32",
                        "http://172.16.150.34",
                        "http://x-ray.cuboxservice.com/") // 허용하려는 IP 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
