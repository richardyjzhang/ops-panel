package com.zhangrichard.ops_panel.service.impl;

import com.zhangrichard.ops_panel.model.Service;
import com.zhangrichard.ops_panel.repository.ServiceRepository;
import com.zhangrichard.ops_panel.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Service> findAllDisk(Integer machineId) {
        return serviceRepository.findAllByMachineId(machineId);
    }

    @Override
    public Service addOneDisk(Service service) {
        Service newService = serviceRepository.save(service);
        return newService;
    }

    @Override
    public Service updateOneDisk(Integer id, Service service) {
        Optional<Service> _service = serviceRepository.findById(id);
        service.setId(null);
        if (_service.isPresent()) {
            service.setId(id);
            // 不允许调整所在机器
            service.setMachineId(_service.get().getMachineId());
            // 不调整运行状态指标
            service.setOnline(_service.get().getOnline());
            serviceRepository.save(service);
        }
        return service;
    }

    @Override
    public void deleteOneDisk(Integer id) {
        serviceRepository.deleteById(id);
    }
}
