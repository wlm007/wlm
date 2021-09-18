package com.wlm.wlm.config;

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
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

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
        web.ignoring().antMatchers("/doc.html", "/", "/sysUser/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 可以自定义登录的相关处理逻辑
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests().anyRequest().authenticated().and()
                .formLogin().loginProcessingUrl("/sysUser/login").usernameParameter("username").passwordParameter("password")
                .permitAll().and().logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .deleteCookies("").permitAll().and().csrf().disable()
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false);

    }
}
