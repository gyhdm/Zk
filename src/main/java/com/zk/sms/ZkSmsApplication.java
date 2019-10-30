package com.zk.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The type Zk sms application.
 * 注解 @EnableJpaAuditing 开启自动填充或更新实体中的CreateDate、CreatedBy等
 * @author guoying
 * @since 2019 -10-29 23:21:34
 */
@SpringBootApplication
@EnableJpaAuditing
public class ZkSmsApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ZkSmsApplication.class, args);
    }

}
