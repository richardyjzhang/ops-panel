package com.zhangrichard.ops_panel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangrichard.ops_panel.model.HeartbeatStatus;
import com.zhangrichard.ops_panel.model.Machine;
import com.zhangrichard.ops_panel.model.MachineDisk;
import com.zhangrichard.ops_panel.repository.MachineDiskRepository;
import com.zhangrichard.ops_panel.repository.MachineRepository;
import com.zhangrichard.ops_panel.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class MqttMessageHandlerServiceImpl {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Autowired
    MachineRepository machineRepository;
    @Autowired
    MachineDiskRepository machineDiskRepository;
    @Autowired
    ServiceRepository serviceRepository;

    @Transactional
    public void handleMessageImpl (Message<String> message) throws JsonProcessingException {
        String payload = message.getPayload();
        HeartbeatStatus status = objectMapper.readValue(payload, HeartbeatStatus.class);

        Optional<Machine> _machine = machineRepository.findById(status.getId());
        if (_machine.isEmpty()) {
            return;
        }

        // 维护服务器状态
        Machine machine = _machine.get();
        machine.setCpuUsage(status.getCpuUsage());
        machine.setRamUsage(status.getRamUsage());
        machine.setOnline(isMachineOnline(status));
        machineRepository.save(machine);

        // 维护硬盘状态
        var disks = machine.getDisks();
        for (MachineDisk disk : disks) {
            // 仅对实际该主机中资源更新，因此外层循环为machine中的disks
            var statusList = status.getDisks();
            for (var heartbeatStatus : statusList) {
                if (heartbeatStatus.getId().equals(disk.getId())) {
                    disk.setDiskUsage(heartbeatStatus.getDiskUsage());
                    machineDiskRepository.save(disk);
                }
            }
        }

        // 维护服务状态
        var services = machine.getServices();
        for (var service : services) {
            // 仅对实际该主机中资源更新，因此外层循环为machine中的services
            var statusList = status.getServices();
            for (var heartbeatStatus : statusList) {
                if (heartbeatStatus.getId().equals(service.getId())) {
                    service.setOnline(heartbeatStatus.getOnline());
                    serviceRepository.save(service);
                }
            }
        }

    }

    private static boolean isMachineOnline(HeartbeatStatus status) {
        try {
            LocalDateTime parsedTime = LocalDateTime.parse(status.getTime(), formatter);
            LocalDateTime curTime = LocalDateTime.now();
            long diff = ChronoUnit.MINUTES.between(parsedTime, curTime);

            // 落后5分钟或超前1分钟，认为是离线
            if (diff >= 5 || diff <= -1) {
                return false;
            }
        } catch (DateTimeParseException e) {
            // 时间戳解析失败，判定为离线
            return false;
        }
        return true;
    }
}
