import request from "@/utils/request";

// 登录请求
export async function postLoginRequest(user: API.LoginUser) {
  const response = await request("/api/ops-panel/login", {
    method: "POST",
    data: user,
  });
  return response;
}

// 登出请求
export async function postLogoutRequest() {
  const response = await request("/api/ops-panel/logout", {
    method: "POST",
  });
  return response;
}

// 获取服务器最新状态
export async function fetchStatusRequest() {
  const response = await request("/api/ops-panel/status");
  return response;
}
