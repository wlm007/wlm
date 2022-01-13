package com.wlm.wlm.controller.wx;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.model.wx.WxMenu;
import com.wlm.wlm.model.wx.WxProperties;
import com.wlm.wlm.util.WxUtils;
import com.wlm.wlm.util.XmlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author wuliming
 * @date 2021/12/15 14:23
 */
@Api(tags = "微信公众号")
@RestController
@RequestMapping("/wx")
public class WxController {

    private WxProperties wxProperties;

    @Autowired
    public void setWxProperties(WxProperties wxProperties) {
        this.wxProperties = wxProperties;
    }

    private WxUtils wxUtils;

    @Autowired
    public void setWxUtils(WxUtils wxUtils) {
        this.wxUtils = wxUtils;
    }


    @ApiOperation(value = "获取微信 access_token")
    @ApiOperationSupport(author = "wlm", order = 1)
    @GetMapping("/get_token")
    public String getToken() {
        return wxUtils.getAccessToken(wxProperties.getAppId(), wxProperties.getAppSecret());
    }


    @ApiOperation(value = "创建自定义菜单")
    @ApiOperationSupport(author = "wlm", order = 2)
    @GetMapping("/create_menu")
    public int createMenu() {
        WxMenu menu = XmlUtils.menuParse();
        return wxUtils.createMenu(menu);
    }

    @ApiOperation(value = "文件转换测试")
    @ApiOperationSupport(author = "wlm", order = 3)
    @PostMapping("/upload_img")
    public String uploadImg(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            try {
                // MultipartFile -> File
                String fileNewName = String.valueOf(System.currentTimeMillis());
                String filePath  = "D:\\uploadFile" ;
                File mm = new File(filePath + File.pathSeparator + fileNewName + ".jpg");
                FileUtils.copyInputStreamToFile(file.getInputStream(), mm);
                System.out.println(mm);

                // File -> MultipartFile
                File newFile = new File("src/test/resources/input.txt");
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

    @ApiOperation(value = "微信curl上传文件测试")
    @ApiOperationSupport(author = "wlm", order = 4)
    @GetMapping("/upload_media")
    public String uploadMedia() {
        return wxUtils.uploadImgByCurl("");
    }


    @ApiOperation(value = "微信素材上传")
    @ApiOperationSupport(author = "wlm", order = 5)
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return wxUtils.commUpload(file, String.valueOf(System.currentTimeMillis()), "demo");
    }


}
