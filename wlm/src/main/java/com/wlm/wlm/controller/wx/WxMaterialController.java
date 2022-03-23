package com.wlm.wlm.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.model.wx.WxMaterial;
import com.wlm.wlm.service.wx.WxMaterialServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 微信公众号素材管理
 * @author wuliming
 * @date 2022/3/14 17:20
 */
@Api(tags = "微信公众号素材管理")
@RestController
@RequestMapping("/wx")
public class WxMaterialController {

    private WxMaterialServiceImpl wxMaterialService;

    @Autowired
    public void setWxMaterialService(WxMaterialServiceImpl wxMaterialService) {
        this.wxMaterialService = wxMaterialService;
    }

    @ApiOperation(value = "微信永久素材上传")
    @ApiOperationSupport(author = "wlm", order = 1)
    @PostMapping("/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file, @RequestParam String type) {
        return wxMaterialService.upload(file, type);
    }

    @ApiOperation(value = "微信素材列表获取")
    @ApiOperationSupport(author = "wlm", order = 2)
    @GetMapping("/get_material_list/{type}")
    public ApiResult<PageInfoResult<List<WxMaterial>>> getMaterialList(
            @PathVariable("type") String type,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return new ApiResult<>(wxMaterialService.getMaterialList(type, pageNo, pageSize));
    }

    @ApiOperation(value = "从微信公众号获取素材列表")
    @ApiOperationSupport(author = "wlm", order = 3)
    @GetMapping("/get_material_list_from_wx/{type}")
    public JSONObject getMaterialListFromWx(
            @PathVariable("type") String type,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return wxMaterialService.getMaterialListFromWx(type, pageNo, pageSize);
    }

    @ApiOperation(value = "获取单个素材")
    @ApiOperationSupport(author = "wlm", order = 4)
    @GetMapping("/get_material_one")
    public JSONObject getMaterialOne(@RequestParam(name = "mediaId") String mediaId) {
        return wxMaterialService.getMaterialOne(mediaId);
    }

    @ApiOperation(value = "删除单个素材")
    @ApiOperationSupport(author = "wlm", order = 5)
    @GetMapping("/delete_material_one")
    public JSONObject delete(@RequestParam(name = "mediaId") String mediaId) {
        return wxMaterialService.delete(mediaId);
    }
}
