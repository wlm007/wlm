package com.wlm.wlm.contoller;

import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.params.sysUser.SysUserListParams;
import com.wlm.wlm.params.sysUser.SysUserParams;
import com.wlm.wlm.service.SysUserService;
import com.wlm.wlm.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuliming
 */
@Api(value = "用户管理")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ApiResult<SysUserVo> register(@RequestBody SysUserParams params) {
        return new ApiResult<>(sysUserService.register(params));
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ApiResult<SysUserVo> login(@RequestBody SysUserParams params) {
        return new ApiResult<>(sysUserService.login(params));
    }

    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(name = "用户id", value = "id", paramType = "Integer")
    @GetMapping("/getOne/{id}")
    public ApiResult<SysUserVo> getUser(@PathVariable Integer id) {
        return new ApiResult<>(sysUserService.getUser(id));
    }

    @ApiOperation(value = "获取用户列表")
    @PostMapping("/list")
    public ApiResult<PageInfoResult<List<SysUserVo>>> list(@RequestBody SysUserListParams params) {
        return new ApiResult<>(sysUserService.list(params));
    }
}
