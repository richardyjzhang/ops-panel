package com.zhangrichard.ops_panel.service;

import com.zhangrichard.ops_panel.model.Machine;

import java.util.List;

public interface MachineService {

    List<Machine> findAllMachine();
    Machine addOneMachine(Machine machine);
    Machine updateOneMachine(Integer id, Machine machine);
    void deleteOneMachine(Integer id);
}
