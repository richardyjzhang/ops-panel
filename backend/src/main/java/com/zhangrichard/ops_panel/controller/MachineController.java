package com.zhangrichard.ops_panel.controller;

import com.zhangrichard.ops_panel.model.Machine;
import com.zhangrichard.ops_panel.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MachineController {

    @Autowired
    MachineService machineService;

    @GetMapping("/machines")
    public List<Machine> getAllMachines() {
        return machineService.findAllMachine();
    }

    @PostMapping("/machines")
    public  Machine addOneMachine(@RequestBody Machine machine) {
        return machineService.addOneMachine(machine);
    }

    @PutMapping("/machines/{id}")
    public ResponseEntity<Machine> updateOneMachine(@PathVariable Integer id,
                                                            @RequestBody Machine machine) {
        Machine newMachine = machineService.updateOneMachine(id, machine);
        if (newMachine.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newMachine);
    }

    @DeleteMapping("/machines/{id}")
    public void deleteOneMachine(@PathVariable Integer id) {
        machineService.deleteOneMachine(id);
    }
}
