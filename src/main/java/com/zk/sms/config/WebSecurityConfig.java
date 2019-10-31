package com.zk.sms.config;

import com.zk.sms.common.filter.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Web security config.
 *
 * @author guoying
 * @since 2019 -10-28 22:03:23
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * The User details service.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * The Jwt authentication token filter.
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * The Restful access denied handler.
     */
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    /**
     * The Restful authentication entry point.
     */
    @Autowired
    private RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;

    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     *
     * @return 认证管理对象
     */
    @Bean(name = "myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码加密器.
     *
     * @return the password encoder
     * @author guoying
     * @since 2019 /10/28
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Filter invocation security metadata source filter invocation security metadata source.
     *
     * @return the filter invocation security metadata source
     * @author guoying
     * @since 2019 /10/31
     */
    @Bean
    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }

    /**
     * Access decision manager access decision manager.
     *
     * @return the access decision manager
     * @author guoying
     * @since 2019 /10/31
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

    /**
     * 配置全局用户信息
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                //跨域支持
                .cors().and()
                //基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //开始请求权限配置
                .authorizeRequests()
                //跨域请求会先进行一次options请求,无权限认证
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //用户登录允许匿名访问
                .antMatchers("/auth/login", "/auth/manage/login").permitAll()
                //匿名访问资源
                .antMatchers("/v2/api-docs",
                        "/doc.html",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-resources",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/swagge‌​r-ui.html").permitAll()
                .withObjectPostProcessor(new CustomObjectPostProcessor())
                //除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        //未授权处理
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restfulAuthenticationEntryPoint);
    }

    /**
     * 自定义权限验证.
     *
     * @author guoying
     * @since 2019 -10-31 21:51:07
     */
    private class CustomObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
            fsi.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
            fsi.setAccessDecisionManager(accessDecisionManager());
            return fsi;
        }
    }
}
