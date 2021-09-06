package com.wlm.wlm.params.mail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邮件发送参数
 * @author wuliming
 * @date 2021/8/30 10:46
 */
@Data
public class MailParams {

    @ApiModelProperty("发送到")
    private String sendTo;

    @ApiModelProperty("主题")
    private String subject;

    @ApiModelProperty("内容")
    private String content;
}
