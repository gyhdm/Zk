package com.zk.sms.service.impl;

import com.zk.sms.common.service.RedisService;
import com.zk.sms.common.service.impl.BaseServiceImpl;
import com.zk.sms.common.util.JwtTokenUtil;
import com.zk.sms.dao.UserRepository;
import com.zk.sms.model.SysUser;
import com.zk.sms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * The type User service.
 *
 * @author guoying
 * @since 2019 -10-28 22:23:06
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<SysUser,String, UserRepository> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Override
    public SysUser findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info("login user details:{}",userDetails);
        redisService.hashOperations().put(jwtTokenUtil.getTokenHeader(), username, userDetails);
        redisService.setExpireTime(jwtTokenUtil.getTokenHeader(),jwtTokenUtil.getExpiration(), TimeUnit.SECONDS);
        return token;
    }
}
