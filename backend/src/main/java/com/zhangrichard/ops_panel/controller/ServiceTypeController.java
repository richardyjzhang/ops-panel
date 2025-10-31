package com.zhangrichard.ops_panel.controller;

import com.zhangrichard.ops_panel.model.ServiceType;
import com.zhangrichard.ops_panel.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceTypeController {

    @Autowired
    ServiceTypeService serviceTypeService;

    @GetMapping("/service-types")
    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeService.findAllServiceType();
    }

    @PostMapping("/service-types")
    public  ServiceType addOneServiceType(@RequestBody ServiceType serviceType) {
        return serviceTypeService.addOneServiceType(serviceType);
    }

    @PutMapping("/service-types/{id}")
    public ResponseEntity<ServiceType> updateOneServiceType(@PathVariable Integer id,
                                                            @RequestBody ServiceType serviceType) {
        ServiceType newServiceType = serviceTypeService.updateOneServiceType(id, serviceType);
        if (newServiceType.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newServiceType);
    }

    @DeleteMapping("/service-types/{id}")
    public void deleteOneServiceType(@PathVariable Integer id) {
        serviceTypeService.deleteOneServiceType(id);
    }
}
