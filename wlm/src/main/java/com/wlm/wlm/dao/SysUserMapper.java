package com.wlm.wlm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.model.SysUser;
import com.wlm.wlm.params.sysUser.SysUserListParams;
import org.apache.ibatis.annotations.Param;

/**
 * @author wuliming
 * @date 2021/7/28 16:55
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页获取角色信息
     * @param page 分页
     * @param params 条件参数
     * @return 用户列表
     */
    IPage<SysUser> findUserListByRoleNo(Page<SysUser> page, @Param("params") SysUserListParams params);
}
