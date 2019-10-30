package com.zk.sms.service;

import com.zk.sms.model.SysUser;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void insertUser() {
        SysUser user = new SysUser();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setName("超级管理员");
        user.setAvailable(true);
        userService.save(user);
    }
}
