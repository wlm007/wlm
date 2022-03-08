package com.wlm.wlm.config.IBMmq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import javax.jms.Message;

/**
 * @author wuliming
 * @date 2022/2/9 14:18
 */
//@Component
public class IbmReceiver extends MessageListenerAdapter {

    @Override
    @JmsListener(destination = "FOR_WLM")
    public void onMessage(Message message) {
        //必须转换若是不转换直接message.tostring消息的传输有限制。
        //转换成文本消息
        try {
            System.out.println("MQ_send传来的消息对象: " + message);
            MsgModel model = message.getBody(MsgModel.class);
            System.out.println("MQ_send传来的消息内容为:" + model.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
