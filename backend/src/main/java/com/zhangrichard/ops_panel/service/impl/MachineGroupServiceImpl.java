package com.zhangrichard.ops_panel.service.impl;

import com.zhangrichard.ops_panel.model.MachineGroup;
import com.zhangrichard.ops_panel.repository.MachineGroupRepository;
import com.zhangrichard.ops_panel.service.MachineGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineGroupServiceImpl implements MachineGroupService {

    @Autowired
    MachineGroupRepository machineGroupRepository;


    @Override
    public List<MachineGroup> findAllMachineGroup() {
        return machineGroupRepository.findAll();
    }

    @Override
    public MachineGroup addOneMachineGroup(MachineGroup machineGroup) {
        MachineGroup newMachineGroup = machineGroupRepository.save(machineGroup);
        return newMachineGroup;
    }

    @Override
    public MachineGroup updateOneMachineGroup(Integer id, MachineGroup machineGroup) {
        Optional<MachineGroup> _machineGroup = machineGroupRepository.findById(id);
        machineGroup.setId(null);
        if (_machineGroup.isPresent()) {
            machineGroup.setId(id);
            machineGroupRepository.save(machineGroup);
        }
        return machineGroup;
    }

    @Override
    public void deleteOneMachineGroup(Integer id) {
        machineGroupRepository.deleteById(id);
    }
}
