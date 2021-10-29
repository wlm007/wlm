package com.wlm.wlm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.params.sysUser.SysUserAddParams;
import com.wlm.wlm.params.sysUser.SysUserListParams;
import com.wlm.wlm.params.sysUser.SysUserUpdateParams;
import com.wlm.wlm.service.SysUserServiceImpl;
import com.wlm.wlm.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wuliming
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @ApiOperation(value = "注册")
    @ApiOperationSupport(author = "wlm", order = 1)
    @PostMapping("/register")
    public ApiResult<SysUserVo> register(@Validated @RequestBody SysUserAddParams params) {
        return new ApiResult<>(sysUserService.register(params));
    }

    @ApiOperation(value = "获取用户信息")
    @ApiOperationSupport(author = "wlm", order = 3)
    @GetMapping("/getOne/{id}")
    public ApiResult<SysUserVo> getUser(@PathVariable Integer id) {
        return new ApiResult<>(sysUserService.getUser(id));
    }

    @ApiOperation(value = "获取用户列表")
    @ApiOperationSupport(author = "wlm", order = 4)
    @PostMapping("/list")
    public ApiResult<PageInfoResult<List<SysUserVo>>> list(@RequestBody SysUserListParams params) {
        return new ApiResult<>(sysUserService.list(params));
    }

    @ApiOperation(value = "更新用户信息")
    @ApiOperationSupport(author = "wlm", order = 5)
    @PostMapping("/update")
    public ApiResult<Object> update(@Valid @RequestBody SysUserUpdateParams params) {
        sysUserService.update(params);
        return new ApiResult<>();
    }
}
