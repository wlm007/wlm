package com.wlm.wlm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.params.sysRole.SysRoleListParams;
import com.wlm.wlm.params.sysRole.SysRoleSaveParams;
import com.wlm.wlm.service.sys.SysRoleServiceImpl;
import com.wlm.wlm.vo.SysRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统角色controller
 * @author wuliming
 * @date 2021/11/24 15:14
 */
@Api(tags = "系统角色")
@RestController()
@RequestMapping("sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @ApiOperation(value = "获取角色列表")
    @ApiOperationSupport(author = "wlm", order = 1)
    @PostMapping("/list")
    public ApiResult<PageInfoResult<List<SysRoleVo>>> list(@RequestBody SysRoleListParams params) {
        return new ApiResult<>(sysRoleService.list(params));
    }

    @ApiOperation(value = "保存角色")
    @ApiOperationSupport(author = "wlm", order = 2)
    @PostMapping("/save")
    public ApiResult<Object> save(@Valid @RequestBody SysRoleSaveParams params) {
        sysRoleService.save(params);
        return new ApiResult<>();
    }

    @ApiOperation(value = "删除角色")
    @ApiOperationSupport(author = "wlm", order = 2)
    @GetMapping("/delete/{id}")
    public ApiResult<Object> delete(@PathVariable Integer id) {
        sysRoleService.delete(id);
        return new ApiResult<>();
    }
}
