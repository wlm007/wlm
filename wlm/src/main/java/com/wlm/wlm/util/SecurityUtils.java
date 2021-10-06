package com.wlm.wlm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.dto.SysUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * security 工具类
 * @author wuliming
 * @date 2021/9/24 16:30
 */
public final class SecurityUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        SysUserDto userDto;
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof SysUserDto) {
                userDto = (SysUserDto) authentication.getPrincipal();
                userName = userDto.getUsername();
            } else {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    public void sendError(HttpServletResponse response, Exception e, int status, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        if (e != null) {
            e.printStackTrace();
        }
        PrintWriter writer = response.getWriter();
        String res = OBJECT_MAPPER.writer().writeValueAsString(new ApiResult<>(500, message));
        logger.info(">>>>>>> 错误返回: " + res);
        writer.write(res);
        writer.flush();
        writer.close();
    }

    public void sendResponse(HttpServletResponse response, int status, Object object) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        String res = OBJECT_MAPPER.writer().writeValueAsString(new ApiResult<>(object));
        logger.info(">>>>>>> 正常返回: " + res);
        writer.write(res);
        writer.flush();
        writer.close();
    }
}
