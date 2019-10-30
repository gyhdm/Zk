package com.zk.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 RESTFul接口文档配置
 * 文档URL: http://localhost:8090/doc.html.
 *
 * @author guoying
 * @since 2019 -10-28 20:29:18
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * Create restful api docket.
     *
     * @return the docket
     * @author guoying
     * @since 2019 /10/28
     */
    @Bean
    public Docket createRestfulApi() {
        //api文档实例
        //文档类型：DocumentationType.SWAGGER_2
        return new Docket(DocumentationType.SWAGGER_2)
                //api信息
                .apiInfo(apiInfo())
                //构建api选择器
                .select()
                //api选择器选择api的包
                .apis(RequestHandlerSelectors.basePackage("com.zk.sms.controller"))
                //api选择器选择包路径下任何api显示在文档中
                .paths(PathSelectors.any())
                //创建文档
                .build();
    }

    /**
     * Api info api info.
     *
     * @return the api info
     * @author guoying
     * @since 2019 /10/28
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短信平台接口文档")
                .description("接口描述")
                .termsOfServiceUrl("termsOfServiceUrl")
                .version("1.0")
                .build();

    }
}
