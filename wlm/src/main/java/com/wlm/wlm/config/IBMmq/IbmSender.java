package com.wlm.wlm.config.IBMmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;

/**
 * @author wuliming
 * @date 2022/2/9 14:17
 */
//@Component
public class IbmSender {

    private JmsOperations jmsOperations;

    @Autowired
    public void setJmsOperations(JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    public void send(){
        MsgModel msgModel = new MsgModel();
        msgModel.setContent("my message.....");
        jmsOperations.convertAndSend("FOR_WLM", msgModel);
        System.out.println("开始发送消息");
    }
}
