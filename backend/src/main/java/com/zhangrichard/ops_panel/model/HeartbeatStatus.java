package com.zhangrichard.ops_panel.model;

import lombok.Data;

import java.util.List;

@Data
public class HeartbeatStatus {

    private String time;

    private Integer id;

    private Double cpuUsage;

    private Double ramUsage;

    private List<DiskStatus> disks;

    private List<ServiceStatus> services;

    @Data
    public static class DiskStatus {

        private Integer id;

        private Double diskUsage;
    }

    @Data
    public static class ServiceStatus {

        private Integer id;

        private Boolean online;
    }
}
