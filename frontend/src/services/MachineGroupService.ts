import {get, post, put, del} from '@/utils/api';

export async function fetchAllMachineGroupData(): Promise<MachineGroup[]> {
    const response = await get<MachineGroup[]>('/machine-groups');
    return response;
}

export async function addOneMachineGroupData(data: MachineGroup): Promise<MachineGroup> {
    const response = await post<MachineGroup>('/machine-groups', data);
    return response;
}