package com.zk.sms.common.filter;

import com.zk.sms.common.service.RedisService;
import com.zk.sms.common.util.JwtTokenUtil;
import com.zk.sms.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器.
 *
 * @author guoying
 * @since 2019 -10-28 23:25:46
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 Request 中的请求头为 “ Authorization ” 的 token 值
        String authHeader = request.getHeader(this.jwtTokenUtil.getTokenHeader());
        // 验证 值是否以"Bearer "开头
        if (StringUtils.startsWith(authHeader, this.jwtTokenUtil.getTokenHead())) {
            // 截取token中"Bearer "后面的值，
            final String authToken = authHeader.substring(this.jwtTokenUtil.getTokenHead().length());
            // 获取用户账号
            String username = this.jwtTokenUtil.getUsernameFromToken(authToken);
            log.info("JwtAuthenticationTokenFilter[doFilterInternal] checking authentication {} ", username);
            // 验证用户账号是否合法
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                LoginUser userDetails = (LoginUser) redisService.hashOperations().get(jwtTokenUtil.getTokenHeader(), username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user {} setting security context", username);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
