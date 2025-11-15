declare interface MyIdNameInterface {
    id?: number;
    name?: string;
}

declare interface MachineGroup extends MyIdNameInterface {}

declare interface MachineType extends MyIdNameInterface {}

declare interface ServiceType extends MyIdNameInterface {}

declare interface MachineDisk {
  id?: number;
  name?: string;
  machineId?: number;
  diskUsage?: number;
}

declare interface MachineService {
  id?: number;
  name?: string;
  machineId?: number;
  typeId?: number;
  online?: boolean;
}

declare interface Machine {
    id?: number;
    name?: string;
    groupId?: number;
    typeId?: number;
    online?: boolean;
    cpuUsage?: number;
    ramUsage?: number;
    disks?: MachineDisk[];
    services?: MachineService[];
}
