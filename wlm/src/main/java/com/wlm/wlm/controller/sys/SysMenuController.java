package com.wlm.wlm.controller.sys;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.params.sysMenu.SysMenuSaveParams;
import com.wlm.wlm.service.sys.SysMenuServiceImpl;
import com.wlm.wlm.vo.SysMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation(value = "保存菜单")
    @ApiOperationSupport(author = "wlm", order = 2)
    @PostMapping("/save")
    public ApiResult<Object> save(@Valid @RequestBody SysMenuSaveParams params) {
        sysMenuService.save(params);
        return new ApiResult<>();
    }

    @ApiOperation(value = "删除菜单")
    @ApiOperationSupport(author = "wlm", order = 3)
    @GetMapping("/delete/{id}")
    public ApiResult<Object> delete(@PathVariable(value = "id") Integer id) {
        sysMenuService.delete(id);
        return new ApiResult<>();
    }
}
