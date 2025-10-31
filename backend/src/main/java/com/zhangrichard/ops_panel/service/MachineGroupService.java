package com.zhangrichard.ops_panel.service;

import com.zhangrichard.ops_panel.model.MachineGroup;

import java.util.List;

public interface MachineGroupService {

    List<MachineGroup> findAllMachineGroup();
    MachineGroup addOneMachineGroup(MachineGroup machineGroup);
    MachineGroup updateOneMachineGroup(Integer id, MachineGroup machineGroup);
    void deleteOneMachineGroup(Integer id);
}
