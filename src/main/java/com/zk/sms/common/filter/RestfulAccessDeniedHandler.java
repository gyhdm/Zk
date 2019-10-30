package com.zk.sms.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.sms.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当访问接口没有权限时，自定义的返回结果.
 *
 * @author guoying
 * @since 2019 -10-30 10:15:55
 */
@Slf4j
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(objectMapper.writeValueAsString(ResultBody.failure(HttpStatus.FORBIDDEN.value(), "没有相关权限", e.getMessage())));
        response.getWriter().flush();
    }
}
