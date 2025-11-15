import { get, post, put, del } from '@/utils/api';

export async function fetchAllMachineData(): Promise<Machine[]> {
    const response = await get<Machine[]>('/machines');
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