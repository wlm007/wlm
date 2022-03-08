package com.wlm.wlm.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.service.wx.WxServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 微信客服服务
 * @author wuliming
 * @date 2022/2/24 11:13
 */
@Api(tags = "微信客服服务接口")
@RestController
@RequestMapping("/wx")
public class WxServiceController {

    private WxServiceImpl wxService;

    @Autowired
    public void setWxService(WxServiceImpl service) {
        this.wxService = service;
    }

    @ApiOperation(value = "获取客服基本信息列表")
    @ApiOperationSupport(author = "wlm", order = 1)
    @GetMapping("/get_customer_list")
    public JSONObject getList() {
        String getListUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }

    @ApiOperation(value = "获取在线客服列表")
    @ApiOperationSupport(author = "wlm", order = 2)
    @GetMapping("/get_online_customer_list")
    public JSONObject getOnlineList() {
        String getListUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }

    @ApiOperation(value = "添加客服账号")
    @ApiOperationSupport(author = "wlm", order = 3)
    @GetMapping("/add_customer")
    public JSONObject add(@RequestParam(name = "kf_account") String kfAccount, @RequestParam(name = "nickname") String nickname) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"kf_account\":\"" +kfAccount+ "\",\"nickname\":\""+nickname+"\"}";
        return wxService.httpsRequest(url, wxService.getPOST(), outStr);
    }

    @ApiOperation(value = "更新客服账号")
    @ApiOperationSupport(author = "wlm", order = 4)
    @GetMapping("/update_customer")
    public JSONObject update(@RequestParam(name = "kf_account") String kfAccount, @RequestParam(name = "nickname") String nickname) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"kf_account\":\"" +kfAccount+ "\",\"nickname\":\""+nickname+"\"}";
        return wxService.httpsRequest(url, wxService.getPOST(), outStr);
    }

    @ApiOperation(value = "邀请微信用户绑定客服账号")
    @ApiOperationSupport(author = "wlm", order = 5)
    @GetMapping("/invite_customer")
    public JSONObject invite(@RequestParam(name = "kf_account") String kfAccount, @RequestParam(name = "invite_wx") String inviteWx) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"kf_account\":\"" +kfAccount+ "\",\"invite_wx\":\""+inviteWx+"\"}";
        return wxService.httpsRequest(url, wxService.getPOST(), outStr);
    }

    @ApiOperation(value = "上传客服头像")
    @ApiOperationSupport(author = "wlm", order = 6)
    @PostMapping("/upload_head_img/{kfAccount}")
    public JSONObject uploadHeadImg(@RequestParam("file") MultipartFile file, @PathVariable("kfAccount") String kfAccount) {
        String uploadHeadImgUrl = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";
        String url = uploadHeadImgUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken()).replace("KFACCOUNT", kfAccount);
        System.out.println(file.getSize());
        System.out.println(kfAccount);
        return wxService.commUpload(file, String.valueOf(System.currentTimeMillis()), "demo", url);
    }

    @ApiOperation(value = "删除客服账号")
    @ApiOperationSupport(author = "wlm", order = 7)
    @GetMapping("/delete_customer")
    public JSONObject delete(@RequestParam(name = "kf_account") String kfAccount) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken()).replace("KFACCOUNT", kfAccount);
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }

    @ApiOperation(value = "创建一个客服会话")
    @ApiOperationSupport(author = "wlm", order = 8)
    @GetMapping("/add_kf_session")
    public JSONObject addSession(@RequestParam(name = "kf_account") String kfAccount, @RequestParam(name = "opneid") String openId) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfsession/create?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"kf_account\":\"" + kfAccount + "\",\"openid\":\""+ openId +"\"}";
        return wxService.httpsRequest(url, wxService.getPOST(), outStr);
    }

    @ApiOperation(value = "关闭一个客服会话")
    @ApiOperationSupport(author = "wlm", order = 9)
    @GetMapping("/close_kf_session")
    public JSONObject closeSession(@RequestParam(name = "kf_account") String kfAccount, @RequestParam(name = "opneid") String openId) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfsession/close?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"kf_account\":\"" + kfAccount + "\",\"openid\":\""+ openId +"\"}";
        return wxService.httpsRequest(url, wxService.getPOST(), outStr);
    }

    @ApiOperation(value = "通过用户id获取客服会话")
    @ApiOperationSupport(author = "wlm", order = 10)
    @GetMapping("/get_kf_session_by_open_id")
    public JSONObject getSessionByOpenId(@RequestParam(name = "opneid") String openId) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfsession/getsession?access_token=ACCESS_TOKEN&openid=OPENID";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken()).replace("OPENID", openId);
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }

    @ApiOperation(value = "通过客服id获取用户会话")
    @ApiOperationSupport(author = "wlm", order = 11)
    @GetMapping("/get_kf_session_by_kf")
    public JSONObject getSessionByKf(@RequestParam(name = "kf_account") String kfAccount) {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfsession/getsessionlist?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken()).replace("KFACCOUNT", kfAccount);
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }

    @ApiOperation(value = "获取未接入会话列表")
    @ApiOperationSupport(author = "wlm", order = 12)
    @GetMapping("/get_wait_session")
    public JSONObject getWaitSession() {
        String getListUrl = "https://api.weixin.qq.com/customservice/kfsession/getwaitcase?access_token=ACCESS_TOKEN";
        String url = getListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }
}
