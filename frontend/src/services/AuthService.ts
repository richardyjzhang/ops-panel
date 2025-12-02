import { post } from '@/utils/api';

export async function login(user: LoginUser): Promise<LoginResult> {
  const response = await post<LoginResult>('/auth/login', user);
  return response;
}

export async function logout(): Promise<void> {
  await post('/auth/logout');
}