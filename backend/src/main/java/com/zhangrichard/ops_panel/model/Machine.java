package com.zhangrichard.ops_panel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer groupId;

    private Integer typeId;

    private Boolean online;

    private Double cpuUsage;

    private Double ramUsage;

    @OneToMany(targetEntity = MachineDisk.class)
    @JoinColumn(name = "machineId", referencedColumnName = "id")
    @JsonIgnore
    private List<MachineDisk> disks;

    @OneToMany(targetEntity = Service.class)
    @JoinColumn(name = "machineId", referencedColumnName = "id")
    @JsonIgnore
    private List<Service> services;

}
