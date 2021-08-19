package com.wlm.wlm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * knife4j 配置
 * @author wuliming
 * @date 2021/8/13 17:28
 */
@Configuration
public class Knife4jConfiguration {

    @Value("${knife4j.enable:false}")
    private Boolean swaggerEnable;

    @Bean
    public Docket defaultApi2() {
        String swaggerTitle = "wlm测试项目";
        String swaggerVersion = "1.0";
        String swaggerServiceUrl = "http://localhost:8002/wlm";
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(new ApiInfoBuilder()
                        .title(swaggerTitle)
                        .description("wlm项目测试")
                        .termsOfServiceUrl(swaggerServiceUrl)
                        .version(swaggerVersion)
                        .build())
                //分组名称
                .groupName("wlm项目测试")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.wlm.wlm.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(globalOperation());
    }
    private List<Parameter> globalOperation(){
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        String swaggerDefaultToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNDI3NTcwODI1MTA0NTE5MTcwIiwidXNlcm5hbWUiOiJ3d" +
                "WxpbWluZyIsImV4cCI6MjQ5MzM1NTEzOH0.pA0rGSqduPFo1z0fpWd_zJNSBmSvUiLIW6rTrly1O9iijEzI7YdRbsPWCedXJbW" +
                "MRrxdxxPy25sedNdsXXgl1A";
        tokenPar.name("x-access-token").modelRef(new ModelRef("string"))
                .defaultValue(swaggerDefaultToken)
                .parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
