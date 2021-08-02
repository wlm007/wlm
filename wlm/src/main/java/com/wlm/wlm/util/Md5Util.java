package com.wlm.wlm.util;

import com.alibaba.fastjson.JSON;
import com.wlm.wlm.model.SysUser;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wuliming
 * @date 2021/7/29 10:46
 */
public class Md5Util {

    public static String keyPassword(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String salt = "sgernmrhtmghlgfjr";
            byte[] pwdByte = (password + salt).getBytes(StandardCharsets.UTF_8);
            byte[] digest = md5.digest(pwdByte);
            return new BASE64Encoder().encode(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateToken(SysUser user) {
        return JSON.toJSONString(user);
    }

    public static SysUser getToken(String token) {
        return (SysUser) JSON.parse(token);
    }
}
