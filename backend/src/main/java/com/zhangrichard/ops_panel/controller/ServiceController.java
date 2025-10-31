package com.zhangrichard.ops_panel.controller;

import com.zhangrichard.ops_panel.model.Service;
import com.zhangrichard.ops_panel.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    ServiceService diskService;

    @GetMapping("/machine-services")
    public List<Service> getAllDisks(
            @RequestParam(value = "machineId", required = true) Integer machineId) {
        return diskService.findAllDisk(machineId);
    }

    @PostMapping("/machine-services")
    public Service addOneDisk(@RequestBody Service service) {
        return diskService.addOneDisk(service);
    }

    @PutMapping("/machine-services/{id}")
    public ResponseEntity<Service> updateOneDisk(@PathVariable Integer id,
                                                     @RequestBody Service service) {
        Service newService = diskService.updateOneDisk(id, service);
        if (newService.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newService);
    }

    @DeleteMapping("/machine-services/{id}")
    public void deleteOneDisk(@PathVariable Integer id) {
        diskService.deleteOneDisk(id);
    }
}
