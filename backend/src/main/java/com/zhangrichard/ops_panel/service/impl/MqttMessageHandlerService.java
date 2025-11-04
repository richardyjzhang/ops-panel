package com.zhangrichard.ops_panel.service.impl;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageHandlerService {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<String> message) {
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        String payload = message.getPayload();
        System.out.println(topic);
        System.out.println(payload);
    }
}
