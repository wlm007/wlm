package com.wlm.wlm.security;

import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security 登录成功自定义处理器
 * @author wuliming
 * @date 2021/9/24 16:06
 */
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info(">>>>>>>>>>> login failure");
        securityUtils.sendError(response, new ApiException(500, "用户名或密码错误"), HttpServletResponse.SC_OK, "用户名或密码错误");
    }
}
