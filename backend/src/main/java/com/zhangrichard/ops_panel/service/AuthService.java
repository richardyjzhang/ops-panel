package com.zhangrichard.ops_panel.service;

import com.zhangrichard.ops_panel.model.LoginUser;

public interface AuthService {

    Boolean login(LoginUser user);
}
