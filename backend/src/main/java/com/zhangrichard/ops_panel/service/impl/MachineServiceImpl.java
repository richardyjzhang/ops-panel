package com.zhangrichard.ops_panel.service.impl;

import com.zhangrichard.ops_panel.model.Machine;
import com.zhangrichard.ops_panel.repository.MachineRepository;
import com.zhangrichard.ops_panel.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    MachineRepository machineRepository;

    @Override
    public List<Machine> findAllMachine() {
        return machineRepository.findAll();
    }

    @Override
    public Machine findOneMachine(Integer id) {
        Optional<Machine> _machine = machineRepository.findById(id);
        return _machine.orElse(null);
    }

    @Override
    public Machine addOneMachine(Machine machine) {
        Machine newMachine = machineRepository.save(machine);
        return newMachine;
    }

    @Override
    public Machine updateOneMachine(Integer id, Machine machine) {
        Optional<Machine> _machine = machineRepository.findById(id);
        machine.setId(null);
        if (_machine.isPresent()) {
            machine.setId(id);
            // 不调整运行状态指标
            machine.setOnline(_machine.get().getOnline());
            machine.setCpuUsage(_machine.get().getCpuUsage());
            machine.setRamUsage(_machine.get().getRamUsage());
            machineRepository.save(machine);
        }
        return machine;
    }

    @Override
    public void deleteOneMachine(Integer id) {
        machineRepository.deleteById(id);
    }
}
