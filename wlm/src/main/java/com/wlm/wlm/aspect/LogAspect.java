package com.wlm.wlm.aspect;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author wuliming
 * @date 2021/8/30 10:56
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    private static final String PATH_INFO ="execution(* com.wlm.wlm.controller..*(..)) && @annotation(annotation)";

    @Before(PATH_INFO)
    public void before(JoinPoint point, ApiOperation annotation) {
        log.info("请求路径说明: " + annotation.value());
    }
}
