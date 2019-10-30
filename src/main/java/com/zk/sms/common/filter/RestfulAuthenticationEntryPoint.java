package com.zk.sms.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.sms.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果.
 *
 * @author guoying
 * @since 2019 -10-30 10:24:25
 */
@Slf4j
@Component
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String msg;
        /**身份认证未通过*/
        if (e instanceof BadCredentialsException) {
            msg = "用户名或密码错误";
        } else {
            msg = "无效的token";
        }
        response.getWriter().println(objectMapper.writeValueAsString(ResultBody.failure(HttpStatus.UNAUTHORIZED.value(), msg, e.getMessage())));
        response.getWriter().flush();
    }
}
