package com.zhangrichard.ops_panel.service;

import com.zhangrichard.ops_panel.model.MachineType;

import java.util.List;

public interface MachineTypeService {

    List<MachineType> findAllMachineType();
    MachineType addOneMachineType(MachineType machineType);
    MachineType updateOneMachineType(Integer id, MachineType machineType);
    void deleteOneMachineType(Integer id);
}
