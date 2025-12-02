package com.zhangrichard.ops_panel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {

    Boolean success;

    String message;
}
