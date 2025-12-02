declare interface MyIdNameInterface {
    id?: number;
    name?: string;
}

declare interface MachineGroup extends MyIdNameInterface {}

declare interface MachineType extends MyIdNameInterface {}

declare interface ServiceType extends MyIdNameInterface {}

declare interface MachineDisk extends MyIdNameInterface {
  machineId?: number;
  diskUsage?: number;
}

declare interface MachineService extends MyIdNameInterface {
  machineId?: number;
  typeId?: number;
  online?: boolean;
}

declare interface Machine extends MyIdNameInterface {
    groupId?: number;
    typeId?: number;
    online?: boolean;
    cpuUsage?: number;
    ramUsage?: number;
    disks?: MachineDisk[];
    services?: MachineService[];
}

declare interface LoginUser {
  username: string;
  password: string;
}

declare interface LoginResult {
  success: boolean;
  message: string;
}