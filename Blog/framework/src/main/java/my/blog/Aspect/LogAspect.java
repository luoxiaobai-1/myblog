package my.blog.Aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import my.blog.annotation.SystemLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(my.blog.annotation.SystemLog)")
    public void pt() {
    }

    @Around("pt()")
    public Object pringlog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj = null;
       try {
           handlebefore(joinPoint);
           obj=joinPoint.proceed();
           handleafter( obj);
       }
       finally {
        log.info("=====End====="+System.lineSeparator());
       }


        return obj;

}

    private void handleafter(Object obj) {

        log.info("RESPONSERESULT==  {}",JSON.toJSON(obj));

    }

    private void handlebefore(ProceedingJoinPoint joinPoint) {
        SystemLog systemLog=getsystemlog(joinPoint);
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=requestAttributes.getRequest();
        log.info("=====START=====");
        log.info("URL==  {}",request.getRequestURI());
        log.info("BUSINESSNAME==  {}",systemLog.buinessname());
        log.info("Http method  ==  {}",request.getMethod());
        log.info("class method  ==  {}.{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        log.info("IP ==  {}",request.getRemoteHost());
        log.info("request args ==  {}", JSON.toJSON(joinPoint.getArgs()));


    }

    private SystemLog getsystemlog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
      return   methodSignature.getMethod().getAnnotation(SystemLog.class);
    }


}

