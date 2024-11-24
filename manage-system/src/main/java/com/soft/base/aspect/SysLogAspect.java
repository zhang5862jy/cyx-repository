package com.soft.base.aspect;

import com.alibaba.fastjson2.JSON;
import com.soft.base.annotation.SysLog;
import com.soft.base.dto.LogDto;
import com.soft.base.rabbitmq.producer.SysLogProduce;
import com.soft.base.resultapi.R;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.soft.base.constants.BaseConstant.HEADER_USER_AGENT;
import static com.soft.base.constants.BaseConstant.LEFT_SLASH;


/**
 * @Author: cyx
 * @Description: 日志拦截器
 * @DateTime: 2024/11/21 10:48
 **/


@Slf4j
@Aspect
@Component
public class SysLogAspect {

    private final SysLogProduce sysLogProduce;

    private final HttpServletRequest servletRequest;

    @Value(value = "${log.enable}")
    private boolean logEnable;

    @Autowired
    public SysLogAspect(SysLogProduce sysLogProduce, HttpServletRequest servletRequest) {
        this.sysLogProduce = sysLogProduce;
        this.servletRequest = servletRequest;
    }

    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint joinPoint, SysLog sysLog) throws Throwable {
        Object result = null;
        if (logEnable) {
            long start = System.currentTimeMillis();
            LogDto logDto = new LogDto();
            logDto.setModuleName(sysLog.module().getName());
            logDto.setOperationDesc(sysLog.value());
            logDto.setType(sysLog.type().getCode());
            //joinPoint.getSignature().toShortString()
            logDto.setRequestMethod(servletRequest.getMethod());
            logDto.setRequestUrl(servletRequest.getRequestURL().toString());
            logDto.setIpAddress(servletRequest.getRemoteAddr());
            logDto.setRequestParams(JSON.toJSONString(joinPoint.getArgs()));

            try {
                result = joinPoint.proceed();
                logDto.setResponseResult(result != null ? result.toString() : null);
                logDto.setStatusCode(result != null ? ((R)result).getCode() : null);

                // 获取 User-Agent
                String userAgentString = servletRequest.getHeader(HEADER_USER_AGENT);
                UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
                String osName = userAgent.getOperatingSystem().getName();
                String browserName = userAgent.getBrowser().getName();
                logDto.setOsBrowserInfo(osName + LEFT_SLASH + browserName);
            } catch (Throwable throwable) {
                logDto.setExceptionInfo(throwable.getMessage());
                throw throwable;
            } finally {
                logDto.setExecutionTime(System.currentTimeMillis() - start);
                sysLogProduce.sendSysLog(logDto);
            }
        }
        return result;
    }
}
