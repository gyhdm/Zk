package com.zk.sms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域访问设置.
 * 必须在Spring Security之前处理CORS
 * @author guoying
 * @since 2019 -10-28 21:14:36
 */
@Slf4j
@Configuration
//@AutoConfigureBefore()
public class GlobalCorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("跨域已设置");
        //添加映射路径
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins(CorsConfiguration.ALL)
                //放行哪些原始域(头部信息)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true);
    }
}
