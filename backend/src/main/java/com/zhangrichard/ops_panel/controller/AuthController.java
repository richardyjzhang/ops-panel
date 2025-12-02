package com.zhangrichard.ops_panel.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zhangrichard.ops_panel.model.LoginResult;
import com.zhangrichard.ops_panel.model.LoginUser;
import com.zhangrichard.ops_panel.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/auth/login")
    public LoginResult login(@RequestBody LoginUser user) {
        LoginResult result = new LoginResult(false, "");

        if (authService.login(user)) {
            result.setSuccess(true);
        }

        return result;
    }

    @PostMapping("/auth/logout")
    public void logout() {
        StpUtil.logout();
    }

}
