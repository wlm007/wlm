package com.wlm.wlm.security;

import com.wlm.wlm.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security 角色控制
 * @author wuliming
 * @date 2021/9/24 17:55
 */
@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        logger.info(">>>>>>>>>>>>>>> login error");
        securityUtils.sendError(response, e, HttpServletResponse.SC_UNAUTHORIZED, "该用户无访问权限");
    }
}
