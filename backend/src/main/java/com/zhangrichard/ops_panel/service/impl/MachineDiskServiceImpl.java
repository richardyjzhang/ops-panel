package com.zhangrichard.ops_panel.service.impl;

import com.zhangrichard.ops_panel.model.MachineDisk;
import com.zhangrichard.ops_panel.repository.MachineDiskRepository;
import com.zhangrichard.ops_panel.service.MachineDiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineDiskServiceImpl implements MachineDiskService {

    @Autowired
    MachineDiskRepository machineDiskRepository;

    @Override
    public List<MachineDisk> findAllDisk(Integer machineId) {
        return machineDiskRepository.findAllByMachineId(machineId);
    }

    @Override
    public MachineDisk addOneDisk(MachineDisk machineDisk) {
        MachineDisk newMachineDisk = machineDiskRepository.save(machineDisk);
        return newMachineDisk;
    }

    @Override
    public MachineDisk updateOneDisk(Integer id, MachineDisk machineDisk) {
        Optional<MachineDisk> _disk = machineDiskRepository.findById(id);
        machineDisk.setId(null);
        if (_disk.isPresent()) {
            machineDisk.setId(id);
            // 不允许调整所在机器
            machineDisk.setMachineId(_disk.get().getMachineId());
            // 不调整运行状态指标
            machineDisk.setDiskUsage(_disk.get().getDiskUsage());
            machineDiskRepository.save(machineDisk);
        }
        return machineDisk;
    }

    @Override
    public void deleteOneDisk(Integer id) {
        machineDiskRepository.deleteById(id);
    }
}
