declare interface MyIdNameInterface {
    id?: number;
    name?: string;
}

declare interface MachineGroup extends MyIdNameInterface {}

declare interface MachineType extends MyIdNameInterface {}

declare interface ServiceType extends MyIdNameInterface {}