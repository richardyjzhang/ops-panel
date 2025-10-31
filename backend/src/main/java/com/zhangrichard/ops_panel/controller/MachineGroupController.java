package com.zhangrichard.ops_panel.controller;

import com.zhangrichard.ops_panel.model.MachineGroup;
import com.zhangrichard.ops_panel.service.MachineGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MachineGroupController {

    @Autowired
    MachineGroupService machineGroupService;

    @GetMapping("/machine-groups")
    public List<MachineGroup> getAllMachineGroups() {
        return machineGroupService.findAllMachineGroup();
    }

    @PostMapping("/machine-groups")
    public  MachineGroup addOneMachineGroup(@RequestBody MachineGroup machineGroup) {
        return machineGroupService.addOneMachineGroup(machineGroup);
    }

    @PutMapping("/machine-groups/{id}")
    public ResponseEntity<MachineGroup> updateOneMachineGroup(@PathVariable Integer id,
                                              @RequestBody MachineGroup machineGroup) {
        MachineGroup newMachineGroup = machineGroupService.updateOneMachineGroup(id, machineGroup);
        if (newMachineGroup.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newMachineGroup);
    }

    @DeleteMapping("/machine-groups/{id}")
    public void deleteOneMachineGroup(@PathVariable Integer id) {
        machineGroupService.deleteOneMachineGroup(id);
    }
}
