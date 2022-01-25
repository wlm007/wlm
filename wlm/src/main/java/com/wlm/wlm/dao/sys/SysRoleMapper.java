package com.wlm.wlm.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.model.sys.SysRole;
import com.wlm.wlm.params.sysRole.SysRoleListParams;
import com.wlm.wlm.vo.SysRoleVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author wuliming
 * @date 2021-09-16
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询角色列表
     * @param params 参数
     * @param page 分页参数
     * @return 角色列表
     */
    IPage<SysRoleVo> list(@Param("params") SysRoleListParams params, Page<SysRoleVo> page);
}