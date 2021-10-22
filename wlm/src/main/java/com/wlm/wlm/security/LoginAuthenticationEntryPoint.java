package com.wlm.wlm.security;

import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security 登录超时控制
 * @author wuliming
 * @date 2021/9/24 17:56
 */
@Component
public class LoginAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info(">>>>>>>>>>>>> logout time");
        logger.error(e.getMessage());
        securityUtils.sendError(response, HttpServletResponse.SC_OK, ApiResult.NOT_LOGIN_OR_OUT, "登录超时或未登录，请重新登录");
    }
}
