package com.wlm.wlm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wlm.wlm.config.IBMmq.IbmSender;
import com.wlm.wlm.config.mqtt.MqttSender;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wuliming
 * @date 2022/1/12 10:31
 */
//@Api(tags = "mqtt测试")
//@RestController
//@RequestMapping("/mqtt")
public class MqttController {

    private MqttSender sender;

    @Autowired
    public void setSender(MqttSender sender) {
        this.sender = sender;
    }

    private IbmSender ibmSender;

    @Autowired
    public void setIbmSender(IbmSender ibmSender) {this.ibmSender = ibmSender;}

    @ApiOperation(value = "mqtt发送测试")
    @ApiOperationSupport(author = "wlm", order = 1)
    @GetMapping("/send")
    public String mqttSend() {
        sender.send();
        return "SUCCESS";
    }

    @ApiOperation(value = "mqtt发送测试2")
    @ApiOperationSupport(author = "wlm", order = 2)
    @GetMapping("/send2")
    public String mqttSend2() {
        sender.sendMessage();
        return "SUCCESS";
    }

    @ApiOperation(value = "ibmMq 发送消息测试")
    @ApiOperationSupport(author = "wlm", order = 3)
    @GetMapping("/ibm_send")
    public String ibmSend() {
        ibmSender.send();
        return "SUCCESS";
    }
}
