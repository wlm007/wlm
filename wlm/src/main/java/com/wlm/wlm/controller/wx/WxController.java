package com.wlm.wlm.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.model.wx.WxMenu;
import com.wlm.wlm.model.wx.WxUsers;
import com.wlm.wlm.model.wx.WxUsersSign;
import com.wlm.wlm.params.wx.BatchAddBlackParams;
import com.wlm.wlm.params.wx.SignToUserParams;
import com.wlm.wlm.service.wx.WxServiceImpl;
import com.wlm.wlm.service.wx.WxUsersServiceImpl;
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
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * 微信公众号相关请求处理
 * @author wuliming
 * @date 2021/12/15 14:23
 */
@Api(tags = "微信公众号接口测试")
@RestController
@RequestMapping("/wx")
public class WxController {

    private WxServiceImpl wxService;

    @Autowired
    public void setWxService(WxServiceImpl wxService) {
        this.wxService = wxService;
    }

    private WxUsersServiceImpl usersService;

    @Autowired
    public void setUsersService(WxUsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/weixin")
    public String inWeiXin(String signature,String nonce,String timestamp,String echostr){
        System.out.println(signature+"--"+nonce+"--"+timestamp+"--"+echostr);
        return echostr;
    }

    @PostMapping("/weixin")
    public void msgWeiXin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("接收到了微信发来的请求");
        wxService.handleWxMsg(request, response);
    }

    @ApiOperation(value = "获取微信 access_token")
    @ApiOperationSupport(author = "wlm", order = 1)
    @GetMapping("/get_token")
    public String getToken() {
        return wxService.getAccessToken();
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
    public ApiResult<PageInfoResult<List<WxUsers>>> getUserList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return new ApiResult<>(usersService.getUserList(pageNo, pageSize));
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


    @ApiOperation(value = "用户备注")
    @ApiOperationSupport(author = "wlm", order = 13)
    @GetMapping("/user_remark")
    public ApiResult<Object> remarkUser(@RequestParam(value = "openId") String openId,
                                 @RequestParam(value = "remark") String remark) {
        usersService.setUserRemark(openId, remark);
        return new ApiResult<>();
    }

    @ApiOperation(value = "添加用户标签")
    @ApiOperationSupport(author = "wlm", order = 14)
    @GetMapping("/add_user_sign")
    public ApiResult<Object> addUserSign(@RequestParam(value = "signName") String signName) {
        usersService.addUserSign(signName);
        return new ApiResult<>();
    }

    @ApiOperation(value = "获取用户标签列表")
    @ApiOperationSupport(author = "wlm", order = 15)
    @GetMapping("/get_user_sign_list")
    public ApiResult<PageInfoResult<List<WxUsersSign>>> userSignList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return new ApiResult<>(usersService.userSignList(pageNo, pageSize));
    }

    @ApiOperation(value = "编辑用户标签")
    @ApiOperationSupport(author = "wlm", order = 16)
    @GetMapping("/edit_user_sign")
    public ApiResult<Object> editUserSign(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "signName") String signName) {
        usersService.editUserSign(id, signName);
        return new ApiResult<>();
    }

    @ApiOperation(value = "删除用户标签")
    @ApiOperationSupport(author = "wlm", order = 17)
    @GetMapping("/delete_user_sign/{id}")
    public ApiResult<Object> deleteUserSign(@PathVariable("id") Integer id) {
        usersService.deleteUserSign(id);
        return new ApiResult<>();
    }

    @ApiOperation(value = "获取用户身上的标签列表")
    @ApiOperationSupport(author = "wlm", order = 18)
    @GetMapping("/get_users_sign_list")
    public ApiResult<List<WxUsersSign>> getUsersSignList(@RequestParam(value = "openid") String openid) {
        return new ApiResult<>(usersService.getUsersSignList(openid));
    }

    @ApiOperation(value = "获取标签下粉丝列表")
    @ApiOperationSupport(author = "wlm", order = 19)
    @GetMapping("/get_users_by_sign")
    public ApiResult<List<WxUsers>> getUserListBySign(
            @RequestParam(value = "signId") Integer signId,
            @RequestParam(value = "openid", required = false) String openid) {
        return new ApiResult<>(usersService.getUserListBySign(signId, openid));
    }

    @ApiOperation(value = "批量给用户打标签")
    @ApiOperationSupport(author = "wlm", order = 20)
    @PostMapping("/sign_to_user")
    public ApiResult<Object> signToUser(@RequestBody SignToUserParams params) {
        usersService.signToUser(params);
        return new ApiResult<>();
    }

    @ApiOperation(value = "批量给用户取消标签")
    @ApiOperationSupport(author = "wlm", order = 21)
    @PostMapping("/cancel_sign_to_user")
    public ApiResult<Object> cancelSignToUser(@RequestBody SignToUserParams params) {
        usersService.cancelSignToUser(params);
        return new ApiResult<>();
    }

    @ApiOperation(value = "拉黑用户(一次最多20)")
    @ApiOperationSupport(author = "wlm", order = 22)
    @PostMapping("/batch_add_black")
    public ApiResult<Object> batchAddBlackList(@Valid @RequestBody BatchAddBlackParams params) {
        usersService.batchAddBlackList(params);
        return new ApiResult<>();
    }

    @ApiOperation(value = "取消拉黑用户(一次最多20)")
    @ApiOperationSupport(author = "wlm", order = 23)
    @PostMapping("/batch_del_black")
    public ApiResult<Object> batchDelBlackList(@Valid @RequestBody BatchAddBlackParams params) {
        usersService.batchDelBlackList(params);
        return new ApiResult<>();
    }

    @ApiOperation(value = "获取拉黑用户")
    @ApiOperationSupport(author = "wlm", order = 24)
    @GetMapping("/get_black_list")
    public ApiResult<PageInfoResult<List<WxUsers>>> getBlackList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return new ApiResult<>(usersService.getBlackList(pageNo, pageSize));
    }
}
