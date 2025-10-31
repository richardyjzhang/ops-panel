package com.zhangrichard.ops_panel.repository;

import com.zhangrichard.ops_panel.model.MachineDisk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineDiskRepository extends JpaRepository<MachineDisk, Integer> {

    List<MachineDisk> findAllByMachineId(Integer machineId);
}
