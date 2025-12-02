package com.zhangrichard.ops_panel.repository;

import com.zhangrichard.ops_panel.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginUserRepository extends JpaRepository<LoginUser, Integer> {

    LoginUser findOneByUsername(String username);
}
