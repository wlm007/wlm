package com.wlm.wlm.util;

import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.model.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Objects;

/**
 * jwt token生成及解析 工具类
 * @author wuliming
 * @date 2021/8/17 16:05
 */
public class JwtUtils {

    /**
     * 秘钥
     */
    public static final String SECRET_KEY = "Knl6ay1hcGkq";
    /**
     * token过期时间(分钟)
     */
    public static final int TOKEN_EXPIRE_TIME = 30;

    /**
     * refreshToken过期时间(小时)
     */
    public static final int REFRESH_TOKEN_EXPIRE_TIME = 24;

    /**
     * 生成token，附带用户信息
     * @param sysUser 用户信息
     * @return token
     */
    public static String generateToken(SysUser sysUser) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, TOKEN_EXPIRE_TIME);
        return Jwts.builder().setSubject(sysUser.getId().toString())
                .claim("age", sysUser.getAge())
                .claim("deptNo", sysUser.getDeptNo())
                .claim("roleNo", sysUser.getRoleNo())
                .claim("username", sysUser.getUsername())
                .claim("email", sysUser.getEmail())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, generateKey())
                .compact();
    }

    /**
     * 获取登录者信息
     * @return  用户信息
     */
    public static SysUser getUserInfo(HttpServletRequest request) {
        if (null == request) {
            request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        }
        SysUser user = null;
        try {
            String token = request.getHeader("x-access-token");
            if (StringUtils.isEmpty(token)) {
                return null;
            }
            Claims claims = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token).getBody();
            user = new SysUser();
            getUser(user, claims);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 通过token获取登录者信息
     * @return  用户信息
     */
    public static SysUser getUserInfoByToken(String token) {
        SysUser user = new SysUser();
        try {
            Claims claims = Jwts.parser().setSigningKey(generateKey())
                    .parseClaimsJws(token).getBody();
            getUser(user, claims);
        } catch (Exception e) {
            throw new ApiException(500, "未登录");
        }
        return user;
    }

    private static void getUser(SysUser user, Claims claims) {
        user.setId(Integer.valueOf(claims.getSubject()));
        user.setAge(Integer.valueOf(claims.get("age").toString()));
        user.setDeptNo(claims.get("deptNo").toString());
        user.setRoleNo(claims.get("roleNo").toString());
        user.setUsername(claims.get("username").toString());
        user.setEmail(claims.get("email").toString());
    }

    private static Key generateKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS512.getDescription());
    }
}
