package com.zhangrichard.ops_panel.controller;

import com.zhangrichard.ops_panel.model.MachineDisk;
import com.zhangrichard.ops_panel.service.MachineDiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MachineDiskController {

    @Autowired
    MachineDiskService diskService;

    @GetMapping("/machine-disks")
    public List<MachineDisk> getAllDisks(
            @RequestParam(value = "machineId", required = true) Integer machineId) {
        return diskService.findAllDisk(machineId);
    }

    @PostMapping("/machine-disks")
    public MachineDisk addOneDisk(@RequestBody MachineDisk machineDisk) {
        return diskService.addOneDisk(machineDisk);
    }

    @PutMapping("/machine-disks/{id}")
    public ResponseEntity<MachineDisk> updateOneDisk(@PathVariable Integer id,
                                                     @RequestBody MachineDisk machineDisk) {
        MachineDisk newMachineDisk = diskService.updateOneDisk(id, machineDisk);
        if (newMachineDisk.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newMachineDisk);
    }

    @DeleteMapping("/machine-disks/{id}")
    public void deleteOneDisk(@PathVariable Integer id) {
        diskService.deleteOneDisk(id);
    }
}
