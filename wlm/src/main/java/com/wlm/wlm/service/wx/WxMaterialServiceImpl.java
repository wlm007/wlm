package com.wlm.wlm.service.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.dao.wx.WxMaterialDao;
import com.wlm.wlm.model.wx.WxMaterial;
import com.wlm.wlm.params.wx.WxMaterialListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 微信公众号素材管理service
 *
 * @author wuliming
 * @date 2022/3/14 17:23
 */
@Service
public class WxMaterialServiceImpl {
    private WxServiceImpl wxService;

    @Autowired
    public void setWxService(WxServiceImpl service) {
        this.wxService = service;
    }

    private WxMaterialDao wxMaterialDao;

    @Autowired
    public void setWxMaterialDao(WxMaterialDao dao) {
        this.wxMaterialDao = dao;
    }

    public JSONObject upload(MultipartFile file, String type) {
        String uploadUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
        String url = uploadUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken()).replace("type", type);
        JSONObject result = wxService.commUpload(file, String.valueOf(System.currentTimeMillis()), "demo", url);
        // 将数据保存到数据库
        WxMaterial wxMaterial = new WxMaterial();
        wxMaterial.setType(type);
        wxMaterial.setMediaId(result.getString("media_id"));
        wxMaterial.setUrl(result.getString("url"));
        wxMaterial.setName(result.getString("name"));
        wxMaterial.setUpdateTime(System.currentTimeMillis());
        wxMaterialDao.insert(wxMaterial);
        return result;
    }

    public PageInfoResult<List<WxMaterial>> getMaterialList(String type, Integer pageNo, Integer pageSize) {
        Page<WxMaterial> page = new Page<>(pageNo, pageSize);
        IPage<WxMaterial> result = wxMaterialDao.getMaterialList(type, page);
        return new PageInfoResult<>(result.getTotal(), result.getRecords());
    }

    public JSONObject getMaterialListFromWx(String type, Integer pageNo, Integer pageSize) {
        String materialUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
        String url = materialUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        WxMaterialListParams params = new WxMaterialListParams();
        params.setType(type);
        params.setCount(pageSize);
        // 这里传入的offset是数据起始位置，如0/1则表示从第一条开始 2 表示从第二条开始 所以需要转换传入的pageNo
        params.setOffset(pageNo * pageSize + 1);
        return wxService.httpsRequest(url, wxService.getPOST(), JSON.toJSONString(params));
    }

    public JSONObject getMaterialOne(String mediaId) {
        String getMaterialOneUrl = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
        String url = getMaterialOneUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outputStr = "{\"media_id\":\"" + mediaId + "\"}";
        // 除了图文素材和视频消息 其他类型的素材都是直接返回素材本身 例:图片就返回图片本身
        return wxService.httpsRequest(url, wxService.getPOST(), outputStr);
    }

    public JSONObject delete(String mediaId) {
        String getMaterialOneUrl = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
        String url = getMaterialOneUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outputStr = "{\"media_id\":\"" + mediaId + "\"}";
        return wxService.httpsRequest(url, wxService.getPOST(), outputStr);
    }
}
