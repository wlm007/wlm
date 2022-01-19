package com.wlm.wlm.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.model.wx.WxMenu;
import com.wlm.wlm.model.wx.WxProperties;
import com.wlm.wlm.service.wx.WxServiceImpl;
import com.wlm.wlm.util.XmlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * 微信公众号相关请求处理
 * @author wuliming
 * @date 2021/12/15 14:23
 */
@Api(tags = "微信公众号接口测试")
@RestController
@RequestMapping("/wx")
public class WxController {

    private WxProperties wxProperties;

    @Autowired
    public void setWxProperties(WxProperties wxProperties) {
        this.wxProperties = wxProperties;
    }

    private WxServiceImpl wxService;

    @Autowired
    public void setWxService(WxServiceImpl wxService) {
        this.wxService = wxService;
    }

    @GetMapping("/weixin")
    public String inWeiXin(String signature,String nonce,String timestamp,String echostr){
        System.out.println(signature+"--"+nonce+"--"+timestamp+"--"+echostr);
        return echostr;
    }

    @PostMapping("/weixin")
    public void msgWeiXin(HttpServletRequest request, HttpServletResponse response) {
        wxService.handleWxMsg(request, response);
    }

    @ApiOperation(value = "获取微信 access_token")
    @ApiOperationSupport(author = "wlm", order = 1)
    @GetMapping("/get_token")
    public String getToken() {
        return wxService.getAccessToken(wxProperties.getAppId(), wxProperties.getAppSecret());
    }

    @ApiOperation(value = "创建自定义菜单")
    @ApiOperationSupport(author = "wlm", order = 2)
    @GetMapping("/create_menu")
    public JSONObject createMenu() {
        WxMenu menu = XmlUtils.menuParse();
        return wxService.createMenu(menu);
    }

    @ApiOperation(value = "文件转换测试")
    @ApiOperationSupport(author = "wlm", order = 3)
    @PostMapping("/upload_img")
    public String uploadImg(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            try {
                // MultipartFile -> File
                String fileNewName = String.valueOf(System.currentTimeMillis());
                String filePath  = "D:\\uploadFile\\" ;
                File mm = new File(filePath + File.pathSeparator + fileNewName + ".jpg");
                FileUtils.copyInputStreamToFile(file.getInputStream(), mm);

                // File -> MultipartFile
                File newFile = new File("src/main/resources/input.txt");
                FileInputStream input = new FileInputStream(newFile);
                MultipartFile multipartFile =new MockMultipartFile("file", newFile.getName(), "text/plain", IOUtils.toByteArray(input));
                System.out.println(multipartFile);

                return mm.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("文件是空的");
        }

        return null;
    }

    @ApiOperation(value = "微信永久素材上传")
    @ApiOperationSupport(author = "wlm", order = 4)
    @PostMapping("/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file) {
        return wxService.commUpload(file, String.valueOf(System.currentTimeMillis()), "demo");
    }

    @ApiOperation(value = "微信素材列表获取")
    @ApiOperationSupport(author = "wlm", order = 5)
    @GetMapping("/get_material_list/{type}")
    public JSONObject getMaterialList(@PathVariable("type") String type) {
        return wxService.getMaterialList(type);
    }

    @ApiOperation(value = "发送模板消息")
    @ApiOperationSupport(author = "wlm", order = 6)
    @GetMapping("/send_template")
    public JSONObject sendTemplate() {
        return wxService.sendTemplate();
    }

    @ApiOperation(value = "获取模板列表")
    @ApiOperationSupport(author = "wlm", order = 7)
    @GetMapping("/get_template_list")
    public JSONObject getTemplateList() {
        return wxService.getTemplateList();
    }

    @ApiOperation(value = "创建二维码")
    @ApiOperationSupport(author = "wlm", order = 8)
    @GetMapping("/create_ticket/{isTemporary}/{isStr}")
    public JSONObject createTicket(@PathVariable("isTemporary") boolean isTemporary, @PathVariable("isStr") boolean isStr) {
        return wxService.createTicket(isTemporary, isStr);
    }

    @ApiOperation(value = "获取微信服务器ip地址")
    @ApiOperationSupport(author = "wlm", order = 9)
    @GetMapping("/get_ips")
    public JSONObject getIpList() {
        return wxService.getWeiXinIpList();
    }

    @ApiOperation(value = "获取微信公众号关注用户列表")
    @ApiOperationSupport(author = "wlm", order = 10)
    @GetMapping("/get_user_list")
    public JSONObject getUserList(@RequestParam(value = "openId", required = false) String openId) {
        return wxService.getUserList(openId);
    }

    @ApiOperation(value = "获取媒体模板")
    @ApiOperationSupport(author = "wlm", order = 11)
    @GetMapping("/get_temp_media")
    public JSONObject getTemporaryMedia(@RequestParam(value = "mediaId", required = false) String mediaId) {
        return wxService.getTemporaryMedia(mediaId);
    }


    @ApiOperation(value = "获取公众号的自动回复规则")
    @ApiOperationSupport(author = "wlm", order = 12)
    @GetMapping("/get_reply_rules")
    public JSONObject getWxReplyRules() {
        return wxService.getWxReplyRules();
    }

}
