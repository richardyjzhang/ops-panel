import { get, post, put, del } from '@/utils/api';

export async function fetchAllMachineData(): Promise<Machine[]> {
    const response = await get<Machine[]>('/machines');
    return response;
}

export async function fetchOneMachineData(id: number): Promise<Machine> {
    const response = await get<Machine>(`/machines/${id}`);
    return response;
}

export async function addOneMachineData(data: Machine): Promise<Machine> {
    const response = await post<Machine>('/machines', data);
    return response;
}

export async function updateOneMachineData(data: Machine): Promise<Machine> {
    const response = await put<Machine>(`/machines/${data.id}`, data);
    return response;
}

export async function deleteOneMachineData(data: Machine) {
    await del(`/machines/${data.id}`);
}

export async function addOneMachineDiskData(data: MachineDisk): Promise<MachineDisk> {
    const response = await post<MachineDisk>('/machine-disks', data);
    return response;
}

export async function updateOneMachineDiskData(data: MachineDisk): Promise<MachineDisk> {
    const response = await put<MachineDisk>(`/machine-disks/${data.id}`, data);
    return response;
}

export async function deleteOneMachineDiskData(data: MachineDisk) {
    await del(`/machine-disks/${data.id}`);
}

export async function addOneMachineServiceData(data: MachineService) {
    const response = await post<MachineService>('/machine-services', data);
    return response;
}

export async function updateOneMachineServiceData(data: MachineService) {
    const response = await put<MachineService>(`/machine-services/${data.id}`, data);
    return response;
}

export async function deleteOneMachineServiceData(data: MachineService) {
    await del(`/machine-services/${data.id}`);
}