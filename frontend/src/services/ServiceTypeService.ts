import { get, post, put, del } from '@/utils/api';

export async function fetchAllServiceTypeData(): Promise<ServiceType[]> {
    const response = await get<ServiceType[]>('/service-types');
    return response;
}

export async function addOneServiceTypeData(data: ServiceType): Promise<ServiceType> {
    const response = await post<ServiceType>('/service-types', data);
    return response;
}

export async function updateOneServiceTypeData(data: ServiceType): Promise<ServiceType> {
    const response = await put<ServiceType>(`/service-types/${data.id}`, data);
    return response;
}

export async function deleteOneServiceTypeData(data: ServiceType) {
    await del(`/service-types/${data.id}`);
}