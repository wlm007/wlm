package com.wlm.wlm.config.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

/**
 * @author wuliming
 * @date 2022/2/11 16:49
 */
public class MqttServer {

    private MqttTopic topic11;

    private MqttMessage message;

    public void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName("guest");
        options.setPassword("guest".toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            MqttClient client = new MqttClient("tcp://127.0.0.1:5672", "server11", new MemoryPersistence());
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("断开连接，可以重连");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // subscribe后得到的消息会执行到这里面
                    System.out.println("接收消息主题 : " + topic);
                    System.out.println("接收消息Qos : " + message.getQos());
                    System.out.println("接收消息内容 : " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }
            });
            client.connect(options);

            topic11 = client.getTopic("felix_topic_exchange");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }

    public static void main(String[] args) throws MqttException {
        MqttServer server = new MqttServer();
        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload("hello,topic11".getBytes(StandardCharsets.UTF_8));
        server.publish(server.topic11, server.message);
        System.out.println(server.message.isRetained() + "------retained状态");
    }
}
