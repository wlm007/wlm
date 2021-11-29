package com.wlm.wlm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.params.sysDept.SysDeptListParams;
import com.wlm.wlm.params.sysDept.SysDeptSaveParams;
import com.wlm.wlm.service.SysDeptServiceImpl;
import com.wlm.wlm.vo.SysDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统部门controller
 * @author wuliming
 * @date 2021/10/29 11:45
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {

    @Autowired
    private SysDeptServiceImpl sysDeptService;

    @ApiOperation(value = "获取部门列表(未成树状结构，只是单纯list)")
    @ApiOperationSupport(author = "wlm", order = 1)
    @PostMapping("/list")
    public ApiResult<List<SysDeptVo>> list(@RequestBody SysDeptListParams params) {
        return new ApiResult<>(sysDeptService.list(params));
    }

    @ApiOperation(value = "获取部门列表(树状结构)")
    @ApiOperationSupport(author = "wlm", order = 2)
    @GetMapping("/listOfTree")
    public ApiResult<List<SysDeptVo>> listOfTree() {
        return new ApiResult<>(sysDeptService.listOfTree());
    }

    @ApiOperation(value = "保存部门信息")
    @ApiOperationSupport(author = "wlm", order = 3)
    @PostMapping("/save")
    public ApiResult<Object> save(@Valid @RequestBody SysDeptSaveParams params) {
        sysDeptService.save(params);
        return new ApiResult<>();
    }

    @ApiOperation(value = "删除部门(包含删除子部门)")
    @ApiOperationSupport(author = "wlm", order = 3)
    @GetMapping("/delete/{id}")
    public ApiResult<Object> delete(@PathVariable(value = "id") Integer id) {
        sysDeptService.delete(id);
        return new ApiResult<>();
    }
}
