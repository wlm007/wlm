package com.wlm.wlm.config;

import com.wlm.wlm.security.LoginAccessDeniedHandler;
import com.wlm.wlm.security.LoginAuthenticationEntryPoint;
import com.wlm.wlm.security.LoginFailureHandler;
import com.wlm.wlm.security.LoginSuccessHandler;
import com.wlm.wlm.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * security登录验证
 *
 * @author wuliming
 * @date 2021/8/24 17:48
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginAccessDeniedHandler loginAccessDeniedHandler;

    @Autowired
    private LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定security 获取用户信息的service和密码加密方式
        auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 可以指定那些请求不需要登录认证
        web.ignoring().antMatchers(
                "/doc.html",
                "/",
                "/sysUser/register",
                "/swagger/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/v2/**",
                "/v2/api-docs-ext/**",
                "/swagger-resources/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 可以自定义登录的相关处理逻辑
        http.headers().frameOptions().sameOrigin();
        http.cors().and().
                authorizeRequests().anyRequest().authenticated().and()
                .exceptionHandling()
                .accessDeniedHandler(loginAccessDeniedHandler).authenticationEntryPoint(loginAuthenticationEntryPoint).and()
                .formLogin().loginProcessingUrl("/sysUser/login")
                .successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
                .permitAll().and().logout().logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID").permitAll().and().csrf().disable()
                .sessionManagement()
                .maximumSessions(1).maxSessionsPreventsLogin(false);

    }
}
