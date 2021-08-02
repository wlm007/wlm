package com.wlm.wlm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.dao.SysUserMapper;
import com.wlm.wlm.model.SysUser;
import com.wlm.wlm.params.sysUser.SysUserListParams;
import com.wlm.wlm.params.sysUser.SysUserParams;
import com.wlm.wlm.util.Md5Util;
import com.wlm.wlm.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuliming
 * @date 2021/7/28 17:24
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUserVo register(SysUserParams params) {
        String password = Md5Util.keyPassword(params.getPassword());
        SysUser sysUser = new SysUser();
        sysUser.setUsername(params.getUsername());
        sysUser.setPassword(password);
        sysUserMapper.insert(sysUser);
        SysUserVo result = new SysUserVo(sysUser);
        result.setToken(Md5Util.generateToken(sysUser));
        return result;
    }

    public SysUserVo login(SysUserParams params) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getPassword, Md5Util.keyPassword(params.getPassword()))
                .eq(SysUser::getUsername, params.getUsername()));
        if (user == null) {
            throw new ApiException("该用户不存在");
        }
        return new SysUserVo(user);
    }

    public PageInfoResult<List<SysUserVo>> list(SysUserListParams params) {
        Page<SysUser> page = new Page<>(params.getPageNo(), params.getPageSize());
        IPage<SysUser> userList = sysUserMapper.findUserListByRoleNo(page, params.getRoleNo());
        return new PageInfoResult<>(userList.getTotal(), userList.getRecords().stream().map(SysUserVo::new).collect(Collectors.toList()));
    }
}
