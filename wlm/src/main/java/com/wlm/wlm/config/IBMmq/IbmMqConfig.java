package com.wlm.wlm.config.IBMmq;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author wuliming
 * @date 2022/2/9 11:05
 */
@Slf4j
//@Configuration
public class IbmMqConfig {

    @Value("${project.mq.host}")
    private String host;

    @Value("${project.mq.port}")
    private Integer port;

    @Value("${project.mq.queue-manager}")
    private String queueManager;

    @Value("${project.mq.channel}")
    private String channel;

    @Value("${project.mq.username}")
    private String username;

    @Value("${project.mq.password}")
    private String password;

    @Value("${project.mq.receive-timeout}")
    private Long receiveTimout;

    /**
     * 配置连接工厂:
     * CCSID要与连接到的队列管理器一致，Windows下默认为1381，
     * Linux下默认为1208。1208表示UTF-8字符集，建议把队列管理器的CCSID改为1208
     */
    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() {
        MQQueueConnectionFactory mq = new MQQueueConnectionFactory();
        mq.setHostName(host);
        try {
            mq.setPort(port);
            mq.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            mq.setCCSID(1381);
            mq.setChannel(channel);
            mq.setQueueManager(queueManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mq;
    }

    /**
     * 配置链接认证:
     * 如不须要帐户密码连接能够跳过此步，直接将mqQueueConnectionFactory注入下一步的缓存链接工厂。
     */
    @Bean
    public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) {
        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
        userCredentialsConnectionFactoryAdapter.setUsername(username);
        userCredentialsConnectionFactoryAdapter.setPassword(password);
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
        return userCredentialsConnectionFactoryAdapter;
    }

    /**
     * 配置缓存连接工厂:
     * 不配置该类则每次与MQ交互都需要重新创建连接，大幅降低速度。
     */
    @Bean
    @Primary
    public CachingConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
        cachingConnectionFactory.setSessionCacheSize(500);
        cachingConnectionFactory.setReconnectOnException(true);
        return cachingConnectionFactory;
    }

    /**
     * 配置JMS模板:
     * JmsOperations为JmsTemplate的实现接口。
     * 重要：不设置setReceiveTimeout时，当队列为空，从队列中取出消息的方法将会一直挂起直到队列内有消息
     */
    @Bean
    public JmsOperations jmsOperations(CachingConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setReceiveTimeout(receiveTimout);
        return jmsTemplate;
    }
}
