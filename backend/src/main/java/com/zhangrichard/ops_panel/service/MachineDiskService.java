package com.zhangrichard.ops_panel.service;

import com.zhangrichard.ops_panel.model.MachineDisk;

import java.util.List;

public interface MachineDiskService {

    List<MachineDisk> findAllDisk(Integer machineId);
    MachineDisk addOneDisk(MachineDisk machineDisk);
    MachineDisk updateOneDisk(Integer id, MachineDisk machineDisk);
    void deleteOneDisk(Integer id);
}
