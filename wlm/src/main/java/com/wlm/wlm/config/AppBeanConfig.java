package com.wlm.wlm.config;

import com.wlm.wlm.util.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目bean工厂
 * @author wuliming
 * @date 2021/9/24 16:39
 */
@Configuration
public class AppBeanConfig {

    @Bean
    public SecurityUtils getSecurityUtils() {
        return new SecurityUtils();
    }
}
