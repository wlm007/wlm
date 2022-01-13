package com.wlm.wlm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author wuliming
 */
@SpringBootApplication
@EnableSwagger2WebMvc
public class WlmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WlmApplication.class, args);
    }
}
