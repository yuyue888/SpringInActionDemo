package core.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiUsageLog {

    @Pointcut("execution(* core..*(..)) &&@within(org.springframework.web.bind.annotation.RestController)")
    private void aspectMethod(){}

    @Before("aspectMethod()")
    public void beforeAdvice(JoinPoint joinPoint){
        String method = joinPoint.getTarget().getClass().getCanonicalName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println(method+":" + JSONObject.toJSON(args).toString());
    }
}
