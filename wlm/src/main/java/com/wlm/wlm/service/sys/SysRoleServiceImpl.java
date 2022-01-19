package com.wlm.wlm.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.dao.SysRoleMapper;
import com.wlm.wlm.model.sys.SysRole;
import com.wlm.wlm.params.sysRole.SysRoleListParams;
import com.wlm.wlm.params.sysRole.SysRoleSaveParams;
import com.wlm.wlm.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色service
 * @author wuliming
 * @date 2021/11/24 15:25
 */
@Service
public class SysRoleServiceImpl {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public PageInfoResult<List<SysRoleVo>> list(SysRoleListParams params) {
        Page<SysRoleVo> page = new Page<>(params.getPageNo(), params.getPageSize());
        IPage<SysRoleVo> result = sysRoleMapper.list(params, page);
        return new PageInfoResult<>(result.getTotal(), result.getRecords());
    }

    public void save(SysRoleSaveParams params) {
        SysRole sysRole;
        if (null != params.getId() && params.getId() > 0) {
            sysRole = sysRoleMapper.selectById(params.getId());
            if (sysRole == null) {
                throw new ApiException(ApiResult.ERROR, "该角色不存在");
            }
            sysRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleNo, params.getRoleNo()).ne(SysRole::getId, params.getId()));
            if (sysRole != null) {
                throw new ApiException(ApiResult.ERROR, "该角色编号已存在");
            }
            sysRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleName, params.getRoleName()).ne(SysRole::getId, params.getId()));
            if (sysRole != null) {
                throw new ApiException(ApiResult.ERROR, "该角色名称已存在");
            }
            sysRole = new SysRole();
            sysRole.setId(params.getId());
            sysRole.setRoleNo(params.getRoleNo());
            sysRole.setRoleName(params.getRoleName());
            sysRoleMapper.updateById(sysRole);
        } else {
            sysRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleNo, params.getRoleNo()));
            if (sysRole != null) {
                throw new ApiException(ApiResult.ERROR, "该角色编号已存在");
            }
            sysRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleName, params.getRoleName()));
            if (sysRole != null) {
                throw new ApiException(ApiResult.ERROR, "该角色名称已存在");
            }
            sysRole = new SysRole();
            sysRole.setRoleNo(params.getRoleNo());
            sysRole.setRoleName(params.getRoleName());
            sysRoleMapper.insert(sysRole);
        }
    }

    public void delete(Integer id) {
        sysRoleMapper.deleteById(id);
    }
}
