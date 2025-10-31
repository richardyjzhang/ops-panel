package com.zhangrichard.ops_panel.service.impl;

import com.zhangrichard.ops_panel.model.ServiceType;
import com.zhangrichard.ops_panel.repository.ServiceTypeRepository;
import com.zhangrichard.ops_panel.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<ServiceType> findAllServiceType() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public ServiceType addOneServiceType(ServiceType serviceType) {
        ServiceType newServiceType = serviceTypeRepository.save(serviceType);
        return newServiceType;
    }

    @Override
    public ServiceType updateOneServiceType(Integer id, ServiceType serviceType) {
        Optional<ServiceType> _serviceType = serviceTypeRepository.findById(id);
        serviceType.setId(null);
        if (_serviceType.isPresent()) {
            serviceType.setId(id);
            serviceTypeRepository.save(serviceType);
        }
        return serviceType;
    }

    @Override
    public void deleteOneServiceType(Integer id) {
        serviceTypeRepository.deleteById(id);
    }
}
