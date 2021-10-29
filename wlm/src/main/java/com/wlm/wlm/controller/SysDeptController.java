package com.wlm.wlm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.params.sysDept.SysDeptListParams;
import com.wlm.wlm.service.SysDeptServiceImpl;
import com.wlm.wlm.vo.SysDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
