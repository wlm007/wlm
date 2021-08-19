package com.wlm.wlm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author wuliming
 * 开启swagger2文档 使用注解 @EnableSwagger2
 */
@SpringBootApplication
@EnableSwagger2WebMvc
@MapperScan("com.wlm.wlm.dao")
public class WlmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WlmApplication.class, args);
    }
}
