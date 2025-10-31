package com.zhangrichard.ops_panel.service;

import com.zhangrichard.ops_panel.model.Service;

import java.util.List;

public interface ServiceService {

    List<Service> findAllDisk(Integer machineId);
    Service addOneDisk(Service service);
    Service updateOneDisk(Integer id, Service service);
    void deleteOneDisk(Integer id);
}
