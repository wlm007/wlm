package com.wlm.wlm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域设置
 * @author wuliming
 * @date 2021/8/20 11:45
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping ("/**")
                // 允许跨域携带cookie
                .allowCredentials(true)
                // 这里不能设置 * 需要明确指定允许访问的路径
                .allowedOrigins("http://localhost:7001");
    }
}
