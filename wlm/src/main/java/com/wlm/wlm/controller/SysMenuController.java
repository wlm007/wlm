package com.wlm.wlm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.service.sys.SysMenuServiceImpl;
import com.wlm.wlm.vo.SysMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统菜单
 * @author wuliming
 * @date 2021/10/19 14:33
 */
@Api(tags = "系统菜单")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @ApiOperation(value = "获取所有系统菜单")
    @ApiOperationSupport(author = "wlm", order = 1)
    @GetMapping("/list")
    public ApiResult<List<SysMenuVo>> list() {
        return new ApiResult<>(sysMenuService.list());
    }
}
