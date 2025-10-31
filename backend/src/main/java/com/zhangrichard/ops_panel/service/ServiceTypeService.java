package com.zhangrichard.ops_panel.service;

import com.zhangrichard.ops_panel.model.ServiceType;

import java.util.List;

public interface ServiceTypeService {

    List<ServiceType> findAllServiceType();
    ServiceType addOneServiceType(ServiceType serviceType);
    ServiceType updateOneServiceType(Integer id, ServiceType serviceType);
    void deleteOneServiceType(Integer id);
}
