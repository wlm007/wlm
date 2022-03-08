package com.wlm.wlm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 * MQ消息推送工具列
 *
 * @author Ztrain
 * @date 2020/6/1
 */
public class MqttUtil {

	/**
	 * 发消息
	 * @param publishUrl        消息发送地址
	 * @param topic             主题名称
	 */
	public static void publish(String publishUrl, String topic, String expand) {
		// TODO 优化
		RestTemplate restTemplate = new RestTemplate();
		JSONObject data = new JSONObject();
		data.put("showWindow", 0);
		data.put("module", "sysMessage");
		JSONObject content = new JSONObject();
		content.put("msgType","3");
		// 扩展字段
		content.put("expand", expand);
		content.put("data",data.toJSONString());
		JSONObject params = new JSONObject();
		params.put("qos",0);
		params.put("retain",false);
		params.put("payload", JSON.toJSONString(content));
		params.put("topic",topic);
		restTemplate.postForEntity(publishUrl,params,JSONObject.class);
	}

	/**
	 * 发消息
	 * @param publishUrl        消息发送地址
	 * @param topic             主题名称
	 */
	public static void publish(String publishUrl, String topic) {
		// TODO 优化
		publish(publishUrl, topic, null);
	}

}
