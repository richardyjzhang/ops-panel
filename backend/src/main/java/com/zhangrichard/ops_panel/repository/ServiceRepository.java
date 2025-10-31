package com.zhangrichard.ops_panel.repository;

import com.zhangrichard.ops_panel.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findAllByMachineId(Integer machineId);
}
