package com.wlm.wlm.config.IBMmq;

import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;

/**
 * ibm mq 集群负载均衡测试 接收
 * @author wuliming
 * @date 2022/2/15 17:12
 */
public class IbmMqReceiver {
    public static void main(String[] args) {
        System.out.println("Starting receiver");
        try {
            // 指定mq服务ip地址 如果是本机则可省略
            MQEnvironment.hostname = "172.27.33.75";
            // 指定监听端口
            MQEnvironment.port = 11201;
            // 指定服务连接通道
            MQEnvironment.channel = "CHaa";
            // 指定用户 如果是本机且是同个用户登录则可省略
            MQEnvironment.userID = "mqm";
            MQEnvironment.password = "Wlm19940312";
            // 实列化队列管理器
            MQQueueManager queueManager = new MQQueueManager("QM97");
            // 从指定队列中获取消息
            MQQueue q = queueManager.accessQueue("RECEIVER.IN", MQConstants.MQOO_INPUT_SHARED);
            MQGetMessageOptions mqGMO = new MQGetMessageOptions();
            mqGMO.waitInterval = 10000;
            while (true) {
                MQMessage message = new MQMessage();
                try {
                    q.get(message, mqGMO);
                    System.out.println("Receiver message " + message.readStringOfByteLength(message.getDataLength()));
                } catch (MQException e) {
                    e.printStackTrace();
                    if (e.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                        break;
                    }
                }
            }
            if (q.isOpen()) {
                q.close();
            }
            if (queueManager.isConnected()) {
                queueManager.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
