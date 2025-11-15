import axios from "axios";
import type {InternalAxiosRequestConfig, AxiosInstance, AxiosResponse} from 'axios';

const axiosInstance: AxiosInstance = axios.create({
    baseURL: `${window.location.origin}/api/ops-panel`,
    timeout: 5000,
})

axiosInstance.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        return config;
    },
    (error: any) => {
        return Promise.reject(error);
    }
)

axiosInstance.interceptors.response.use(
    (response: AxiosResponse) => {
        return response;
    },
    (error: any) => {
        return Promise.reject(error);
    }
)

export async function get<T>(url: string, params?: any): Promise<T> {
    const response = await axiosInstance.get<T>(url, {params});
    return response.data;
}

export async function post<T>(url: string, data?: any): Promise<T> {
    const response = await axiosInstance.post<T>(url, data);
    return response.data;
}

export async function put<T>(url: string, data?: any): Promise<T> {
    const response = await axiosInstance.put<T>(url, data);
    return response.data;
}

export async function del<T>(url: string, params?: any): Promise<T> {
    const response = await axiosInstance.delete<T>(url, {params});
    return response.data;
}