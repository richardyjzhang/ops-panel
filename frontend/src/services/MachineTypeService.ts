import { get, post, put, del } from '@/utils/api';

export async function fetchAllMachineTypeData(): Promise<MachineType[]> {
    const response = await get<MachineType[]>('/machine-types');
    return response;
}

export async function addOneMachineTypeData(data: MachineType): Promise<MachineType> {
    const response = await post<MachineType>('/machine-types', data);
    return response;
}

export async function updateOneMachineTypeData(data: MachineType): Promise<MachineType> {
    const response = await put<MachineType>(`/machine-types/${data.id}`, data);
    return response;
}

export async function deleteOneMachineTypeData(data: MachineType) {
    await del(`/machine-types/${data.id}`);
}