package com.zk.sms.common.service.impl;

import com.zk.sms.model.LoginUser;
import com.zk.sms.model.SysUser;
import com.zk.sms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * spring security 验证用户service.
 *
 * @author guoying
 * @since 2019 -10-28 22:50:37
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * The User service.
     */
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.findByUsername(username);
        if (sysUser == null) {
            throw new InternalAuthenticationServiceException("用户名或密码错误");
        }
        return new LoginUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), sysUser.getAvailable());
    }
}
