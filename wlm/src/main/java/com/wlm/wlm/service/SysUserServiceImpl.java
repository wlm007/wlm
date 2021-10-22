package com.wlm.wlm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.dao.SysRoleMapper;
import com.wlm.wlm.dao.SysUserMapper;
import com.wlm.wlm.dto.SysUserDto;
import com.wlm.wlm.model.SysRole;
import com.wlm.wlm.model.SysUser;
import com.wlm.wlm.params.sysUser.SysUserListParams;
import com.wlm.wlm.params.sysUser.SysUserAddParams;
import com.wlm.wlm.util.JwtUtils;
import com.wlm.wlm.util.StringUtils;
import com.wlm.wlm.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuliming
 * @date 2021/7/28 17:24
 */
@Service
public class SysUserServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(rollbackFor = Exception.class)
    public SysUserVo register(SysUserAddParams params) {
        // 判断姓名是否存在
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, params.getUsername()));
        if (user != null) {
            throw new ApiException(ApiResult.ERROR, "用户名已存在");
        }
        String password = passwordEncoder.encode(StringUtils.isNotEmpty(params.getPassword()) ? params.getPassword() : "123456");
        params.setPassword(password);
        SysUser sysUser = params.getSysUser();
        sysUserMapper.insert(sysUser);
        SysUserVo result = new SysUserVo(sysUser);
        result.setToken(JwtUtils.generateToken(sysUser));
        return result;
    }

    public PageInfoResult<List<SysUserVo>> list(SysUserListParams params) {
        Page<SysUser> page = new Page<>(params.getPageNo(), params.getPageSize());
        IPage<SysUser> userList = sysUserMapper.findUserListByRoleNo(page, params);
        return new PageInfoResult<>(userList.getTotal(), userList.getRecords().stream().map(SysUserVo::new).collect(Collectors.toList()));
    }

    public SysUserVo getUser(Integer id) {
        return new SysUserVo(sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, id)));
    }

    /**
     * spring security 自定义获取用户信息的方法
     * 其中用户名从请求路径中获取是使用request.getParameter("username"),但同时只接受post请求
     * 所以前端请求时需要用post请求并且使用 headers:{'Content-Type': 'application/x-www-form-urlencoded'}配置
     * 或者使用路径参 login?username=wlm&password=123456(不推荐)
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户名不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = JwtUtils.getUserInfo(null);
        if (user == null) {
            user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
            if (user == null) {
                throw new ApiException(ApiResult.ERROR, "用户不存在");
            }
        }

        List<SysRole> roleList = sysRoleMapper.selectList(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleNo, user.getRoleNo()));
        SysUserDto userDto = new SysUserDto(user);
        userDto.setRoleList(roleList);
        return userDto;
    }
}
