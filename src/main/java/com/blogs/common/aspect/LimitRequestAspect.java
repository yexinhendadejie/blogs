package com.blogs.common.aspect;

import com.blogs.common.validator.anno.LimitRequest;
import com.blogs.utils.Resp;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.blogs.common.resultcode.ResultCodeEnum.MAXIMUM_NUMBER_OF_REQUESTS;

@Aspect
@Component
public class LimitRequestAspect {
    private static ConcurrentHashMap<String, ExpiringMap<String, Integer>> book = new ConcurrentHashMap<>();

    // 定义切点
    // 让所有有@LimitRequest注解的方法都执行切面方法
    @Pointcut("@annotation(limitRequest)")
    public void excudeService(LimitRequest limitRequest) {
    }

    @Around("excudeService(limitRequest)")
    public Object doAround(ProceedingJoinPoint pjp, LimitRequest limitRequest) throws Throwable {
        // 获得request对象
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        // 获取Map对象， 如果没有则返回默认值
        // 第一个参数是key， 第二个参数是默认值
        ExpiringMap<String, Integer> map = book.getOrDefault(request.getRequestURI(), ExpiringMap.builder().variableExpiration().build());
        Integer uCount = map.getOrDefault(request.getRemoteAddr(), 0);


        if (uCount >= limitRequest.count()) { // 超过次数，不执行目标方法
            //这里的返回对象类型根据controller方法的返回方式一致
            return Resp.error(MAXIMUM_NUMBER_OF_REQUESTS);
        } else if (uCount == 0) { // 第一次请求时，设置开始有效时间
            map.put(request.getRemoteAddr(), uCount + 1, ExpirationPolicy.CREATED, limitRequest.oneMinuteInMillis(), TimeUnit.MILLISECONDS);
        } else { // 未超过次数， 记录数据加一
            map.put(request.getRemoteAddr(), uCount + 1);
        }
        book.put(request.getRequestURI(), map);

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        return result;
    }

}