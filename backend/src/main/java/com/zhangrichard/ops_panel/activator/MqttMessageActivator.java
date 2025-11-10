package com.zhangrichard.ops_panel.activator;

import com.zhangrichard.ops_panel.service.impl.MqttMessageHandlerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageActivator {

    @Autowired
    MqttMessageHandlerServiceImpl handler;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<String> message) {

        try {
            handler.handleMessageImpl(message);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
