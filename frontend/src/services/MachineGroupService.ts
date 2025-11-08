import {get, post, put, del} from '@/utils/api';

export async function fetchAllMachineGroupsData(): Promise<MachineGroup[]> {
    const response = await get<MachineGroup[]>('/machine-groups');
    return response;
}