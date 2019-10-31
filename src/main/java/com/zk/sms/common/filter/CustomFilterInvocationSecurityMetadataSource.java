package com.zk.sms.common.filter;

import com.zk.sms.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

@Slf4j
//@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionService permissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Map<String, Collection<ConfigAttribute>> permissionMap = permissionService.getPermissionMap();
        FilterInvocation filterInvocation = (FilterInvocation) object;
        log.info("request url: {}", filterInvocation.getFullRequestUrl());
        HttpServletRequest httpRequest = filterInvocation.getHttpRequest();
        AntPathRequestMatcher matcher;
        for (String url : permissionMap.keySet()) {
            matcher = new AntPathRequestMatcher(url);
            if (matcher.matches(httpRequest)) {
                return permissionMap.get(url);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
