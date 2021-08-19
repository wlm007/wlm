package com.wlm.wlm.util;

import com.alibaba.fastjson.JSON;
import com.wlm.wlm.model.SysUser;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author wuliming
 * @date 2021/7/29 10:46
 */
public class Md5Util {

    public static String keyPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(SysUser user) {
        return JSON.toJSONString(user);
    }

    public static SysUser getToken(String token) {
        return (SysUser) JSON.parse(token);
    }
}
