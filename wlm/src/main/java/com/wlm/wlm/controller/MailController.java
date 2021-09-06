package com.wlm.wlm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.util.MailSendUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件发送
 * @author wuliming
 * @date 2021/8/30 10:42
 */
@Api(tags = "邮件发送")
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailSendUtil mailSendUtil;

    @ApiOperation(value = "发送邮件")
    @ApiOperationSupport(author = "wlm", order = 5)
    @GetMapping("/sendMail")
    public ApiResult<Object> sendMail() {
        mailSendUtil.sendMimeEmail("1196588740@qq.com", "天朝有限公司", "<h3>fuck you</h3>");
        return new ApiResult<>();
    }
}
