import request from "@/utils/request";

// 登录请求
export async function postLoginRequest(user: API.LoginUser) {
  const response = await request("/api/login", {
    method: "POST",
    data: user,
  });
  return response;
}

// 登出请求
export async function postLogoutRequest() {
  const response = await request("/api/logout", {
    method: "POST",
  });
  return response;
}
