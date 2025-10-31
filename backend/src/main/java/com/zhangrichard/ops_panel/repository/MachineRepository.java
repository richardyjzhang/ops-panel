package com.zhangrichard.ops_panel.repository;

import com.zhangrichard.ops_panel.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Integer> {
}
