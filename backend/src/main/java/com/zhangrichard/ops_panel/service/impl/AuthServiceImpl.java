package com.zhangrichard.ops_panel.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.zhangrichard.ops_panel.model.LoginUser;
import com.zhangrichard.ops_panel.repository.LoginUserRepository;
import com.zhangrichard.ops_panel.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    LoginUserRepository loginUserRepository;

    @Override
    public Boolean login(LoginUser loginUser) {
        LoginUser user = loginUserRepository.findOneByUsername(loginUser.getUsername());
        if (user == null) return false;

        String password = SaSecureUtil.sha256(loginUser.getPassword());
        if (user.getPassword().equals(password)){
            StpUtil.login(user.getId());
            return true;
        }
        return false;
    }
}
