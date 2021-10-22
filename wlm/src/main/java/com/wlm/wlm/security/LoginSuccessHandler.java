package com.wlm.wlm.security;

import com.alibaba.fastjson.JSON;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.dto.SysUserDto;
import com.wlm.wlm.model.SysUser;
import com.wlm.wlm.util.JwtUtils;
import com.wlm.wlm.util.SecurityUtils;
import com.wlm.wlm.vo.SysUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * security 登录成功自定义处理器
 * @author wuliming
 * @date 2021/9/24 16:06
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SecurityUtils securityUtils;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SysUserDto userDto = (SysUserDto) authentication.getPrincipal();
        if (userDto == null) {
            securityUtils.sendError(response, HttpServletResponse.SC_OK, ApiResult.NOT_EXIST, "用户名或密码错误，请重新输入");
            return;
        }
        SysUser user = userDto.getUser();
        SysUserVo result = new SysUserVo(user);
        String token = JwtUtils.generateToken(user);
        result.setToken(token);
        //生成新的refreshToken
        String refreshToken = UUID.randomUUID().toString();
        //数据放入redis
        stringRedisTemplate.opsForHash().put(refreshToken, "token", token);
        stringRedisTemplate.opsForHash().put(refreshToken, "userInfo", JSON.toJSONString(user));
        //设置token的过期时间
        stringRedisTemplate.expire(refreshToken, JwtUtils.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
        result.setRefreshToken(refreshToken);
        logger.info(">>>>>>>>>>>>>>>>> login success");
        securityUtils.sendResponse(response, HttpServletResponse.SC_OK, result);
    }
}
