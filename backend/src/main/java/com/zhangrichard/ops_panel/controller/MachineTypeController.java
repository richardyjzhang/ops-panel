package com.zhangrichard.ops_panel.controller;

import com.zhangrichard.ops_panel.model.MachineType;
import com.zhangrichard.ops_panel.service.MachineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MachineTypeController {

    @Autowired
    MachineTypeService machineTypeService;

    @GetMapping("/machine-types")
    public List<MachineType> getAllMachineTypes() {
        return machineTypeService.findAllMachineType();
    }

    @PostMapping("/machine-types")
    public  MachineType addOneMachineType(@RequestBody MachineType machineType) {
        return machineTypeService.addOneMachineType(machineType);
    }

    @PutMapping("/machine-types/{id}")
    public ResponseEntity<MachineType> updateOneMachineType(@PathVariable Integer id,
                                                            @RequestBody MachineType machineType) {
        MachineType newMachineType = machineTypeService.updateOneMachineType(id, machineType);
        if (newMachineType.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newMachineType);
    }

    @DeleteMapping("/machine-types/{id}")
    public void deleteOneMachineType(@PathVariable Integer id) {
        machineTypeService.deleteOneMachineType(id);
    }
}
