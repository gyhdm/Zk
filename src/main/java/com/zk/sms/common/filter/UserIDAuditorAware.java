package com.zk.sms.common.filter;

import com.zk.sms.model.LoginUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 获取当前创建或修改的用户 .
 *
 * createBy LastModifiedBy
 *
 * @author guoying
 * @since 2019 -10-30 23:58:10
 */
@Configuration
public class UserIDAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext context = SecurityContextHolder.getContext();
        LoginUser principal = (LoginUser) context.getAuthentication().getPrincipal();
        return Optional.ofNullable(principal.getId());
    }
}
