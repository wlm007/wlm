package com.wlm.wlm.config;

import com.wlm.wlm.security.CustomizedLogoutSuccessHandler;
import com.wlm.wlm.util.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomizedLogoutSuccessHandler();
    }
}
