package com.wlm.wlm.config.IBMmq;

import com.ibm.mq.*;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author wuliming
 * @date 2022/2/9 17:37
 */
public class IbmConnTest {

    static MQQueueManager queueManager;

    public static void connect() throws MQException {
        MQEnvironment.hostname = "127.0.0.1";
        MQEnvironment.channel = "QM96Server";
        MQEnvironment.port = 11201;
        MQEnvironment.CCSID = 1381;
        //MQ中拥有权限的用户名
        MQEnvironment.userID = "wuliming";
        //用户名对应的密码
        MQEnvironment.password = "940312";

        queueManager = new MQQueueManager("QM96");

    }

    public static void sendMsg(String msgStr) {
        int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;
        MQQueue queue = null;
        try {
            // 建立Q1通道的连接
            queue = queueManager.accessQueue("FOR_WLM", openOptions, null, null, null);
            // 要写入队列的消息
            MQMessage msg = new MQMessage();
            msg.format = MQC.MQFMT_STRING;
            msg.characterSet = 1381;
            msg.encoding = 1381;
            // msg.writeObject(msgStr); //将消息写入消息对象中
            MsgModel model = new MsgModel();
            model.setContent(msgStr);
            msg.writeObject(model);
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            // 设置消息永不过期
            msg.expiry = -1;
            // 将消息放入队列
            queue.put(msg, pmo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (queue != null) {
                try {
                    queue.close();
                } catch (MQException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void receiveMsg() {
        int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;
        MQQueue queue = null;
        try {
            queue = queueManager.accessQueue("FOR_WLM", openOptions, null, null, null);
            System.out.println("该队列当前的深度为:" + queue.getCurrentDepth());
            System.out.println("===========================");
            int depth = queue.getCurrentDepth();
            // 将队列的里的消息读出来
            while (depth-- > 0) {
                // 要读的队列的消息
                MQMessage msg = new MQMessage();
                MQGetMessageOptions gmo = new MQGetMessageOptions();
                queue.get(msg, gmo);
                System.out.println("消息的大小为：" + msg.getDataLength());
                System.out.println("消息的内容：\n" + msg.readObject());
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (queue != null) {
                try {
                    queue.close();
                } catch (MQException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            connect();
            sendMsg("特殊t");
            receiveMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void test() {
        MQQueueConnectionFactory mq = new MQQueueConnectionFactory();
        mq.setHostName("127.0.0.1");
        try {
            mq.setPort(8930);
            mq.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            mq.setCCSID(1381);
            mq.setChannel("SVRCONN");
            mq.setQueueManager("ECIS_QM");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JmsOperations jmsTemplate = new JmsTemplate(mq);
        jmsTemplate.convertAndSend("QLOCAL_SVC2ADP_4_TELNET", "my message...");
    }
}
