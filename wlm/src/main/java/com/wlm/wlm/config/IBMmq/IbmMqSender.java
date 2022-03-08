package com.wlm.wlm.config.IBMmq;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

/**
 * ibm mq 集群负载均衡测试 发送
 * @author wuliming
 * @date 2022/2/15 16:59
 */
public class IbmMqSender {

    public static void main(String[] args) {
        System.out.println("Starting sender");
        MQQueueManager queueManager = null;
        MQQueue q = null;
        try {
            MQEnvironment.port = 11201;
            MQEnvironment.channel = "QM96Server";
            queueManager = new MQQueueManager("QM96");
            q = queueManager.accessQueue("RECEIVER.IN", MQConstants.MQOO_OUTPUT + MQConstants.MQOO_BIND_NOT_FIXED);
            for (int i = 0; i < 5; i++) {
                MQMessage message = new MQMessage();
                message.writeString("test message " + i);
                q.put(message);
                System.out.println("put message " + i);
            }
            q.close();
            queueManager.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (q != null && q.isOpen()) {
                    q.close();
                }
                if (queueManager != null && queueManager.isConnected()) {
                    queueManager.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
