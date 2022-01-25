package com.wlm.wlm.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.dao.sys.SysRoleMapper;
import com.wlm.wlm.dao.sys.SysUserMapper;
import com.wlm.wlm.dto.SysUserDto;
import com.wlm.wlm.model.sys.SysRole;
import com.wlm.wlm.model.sys.SysUser;
import com.wlm.wlm.params.sysUser.SysUserAddParams;
import com.wlm.wlm.params.sysUser.SysUserListParams;
import com.wlm.wlm.params.sysUser.SysUserUpdateParams;
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
        // 密码如果未设置则默认为123456
        String password = passwordEncoder.encode(StringUtils.isNotEmpty(params.getPassword()) ? params.getPassword() : "123456");
        params.setPassword(password);
        // 用户角色如果未输入则默认为普通用户
        params.setRoleNo(StringUtils.isNotEmpty(params.getRoleNo()) ? params.getRoleNo() : "user");
        SysUser sysUser = params.getSysUser();
        sysUserMapper.insert(sysUser);
        SysUserVo result = new SysUserVo(sysUser);
        result.setToken(JwtUtils.generateToken(sysUser));
        return result;
    }

    public PageInfoResult<List<SysUserVo>> list(SysUserListParams params) {
        Page<SysUserVo> page = new Page<>(params.getPageNo(), params.getPageSize());
        IPage<SysUserVo> result = sysUserMapper.findUserListByRoleNo(page, params);
        return new PageInfoResult<>(result.getTotal(), result.getRecords());
    }

    public SysUserVo getUser(Integer id) {
        return sysUserMapper.selectOneById(id);
    }

    public void deleteUser(Integer id) {
        sysUserMapper.deleteById(id);
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

        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (user == null) {
            throw new ApiException(ApiResult.ERROR, "用户不存在");
        }

        List<SysRole> roleList = sysRoleMapper.selectList(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleNo, user.getRoleNo()));
        SysUserDto userDto = new SysUserDto(user);
        userDto.setRoleList(roleList);
        return userDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserUpdateParams params) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, params.getUsername()));
        if (user != null && !user.getId().equals(params.getId())) {
            throw new ApiException(ApiResult.ERROR, "用户名已存在，请重新输入");
        }
        SysUser updateUser = new SysUser();
        updateUser.setId(params.getId());
        updateUser.setUsername(params.getUsername());
        updateUser.setAge(params.getAge());
        updateUser.setEmail(params.getEmail());
        updateUser.setDeptNo(params.getDeptNo());
        updateUser.setRoleNo(params.getRoleNo());
        sysUserMapper.updateById(updateUser);
    }
}
