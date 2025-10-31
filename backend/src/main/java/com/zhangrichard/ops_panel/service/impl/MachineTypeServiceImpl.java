package com.zhangrichard.ops_panel.service.impl;

import com.zhangrichard.ops_panel.model.MachineType;
import com.zhangrichard.ops_panel.repository.MachineTypeRepository;
import com.zhangrichard.ops_panel.service.MachineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineTypeServiceImpl implements MachineTypeService {

    @Autowired
    MachineTypeRepository machineTypeRepository;

    @Override
    public List<MachineType> findAllMachineType() {
        return machineTypeRepository.findAll();
    }

    @Override
    public MachineType addOneMachineType(MachineType machineType) {
        MachineType newMachineType = machineTypeRepository.save(machineType);
        return newMachineType;
    }

    @Override
    public MachineType updateOneMachineType(Integer id, MachineType machineType) {
        Optional<MachineType> _machineType = machineTypeRepository.findById(id);
        machineType.setId(null);
        if (_machineType.isPresent()) {
            machineType.setId(id);
            machineTypeRepository.save(machineType);
        }
        return machineType;
    }

    @Override
    public void deleteOneMachineType(Integer id) {
        machineTypeRepository.deleteById(id);
    }
}
