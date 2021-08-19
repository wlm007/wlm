package com.wlm.wlm.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.dao.SysUserMapper;
import com.wlm.wlm.model.SysUser;
import com.wlm.wlm.params.sysUser.SysUserListParams;
import com.wlm.wlm.params.sysUser.SysUserParams;
import com.wlm.wlm.util.JwtUtils;
import com.wlm.wlm.util.Md5Util;
import com.wlm.wlm.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wuliming
 * @date 2021/7/28 17:24
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public SysUserVo register(SysUserParams params) {
        // 判断姓名是否存在
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, params.getUsername()));
        if (user != null) {
            throw new ApiException(500, "用户名已存在");
        }
        String password = Md5Util.keyPassword(params.getPassword());
        SysUser sysUser = new SysUser();
        sysUser.setUsername(params.getUsername());
        sysUser.setPassword(password);
        sysUserMapper.insert(sysUser);
        SysUserVo result = new SysUserVo(sysUser);
        result.setToken(JwtUtils.generateToken(sysUser));
        return result;
    }

    public SysUserVo login(SysUserParams params) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, params.getUsername()));
        if (user == null) {
            throw new ApiException("该用户不存在");
        } else {
            if (params.getPassword() != null && !Md5Util.keyPassword(params.getPassword()).equals(user.getPassword())) {
                throw new ApiException("密码错误，请重新输入");
            }
        }
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
        return result;
    }

    public PageInfoResult<List<SysUserVo>> list(SysUserListParams params) {
        Page<SysUser> page = new Page<>(params.getPageNo(), params.getPageSize());
        IPage<SysUser> userList = sysUserMapper.findUserListByRoleNo(page, params);
        return new PageInfoResult<>(userList.getTotal(), userList.getRecords().stream().map(SysUserVo::new).collect(Collectors.toList()));
    }

    public SysUserVo getUser(Long id) {
        return new SysUserVo(sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, id)));
    }
}
