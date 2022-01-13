package com.wlm.wlm.aspect;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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

        RequestAttributes requestAttribute = RequestContextHolder.getRequestAttributes();
        if (requestAttribute == null) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttribute).getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("CLASS_METHOD : " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(point.getArgs()));
    }
}
