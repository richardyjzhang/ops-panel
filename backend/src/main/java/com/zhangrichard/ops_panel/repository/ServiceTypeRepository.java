package com.zhangrichard.ops_panel.repository;

import com.zhangrichard.ops_panel.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {
}
