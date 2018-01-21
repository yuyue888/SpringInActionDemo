package core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrintUsingTime {

    @Pointcut("execution(* core..*(..)) &&@annotation(core.aop.CalculateTime)")
    private void aspectMethod(){}

    @Around("aspectMethod()")
    public Object beforeAdvice(ProceedingJoinPoint joinPoint){
        Object obj =null;
        try {
            long startTimemills  =System.currentTimeMillis();
            obj = joinPoint.proceed();
            long endTimemills  =System.currentTimeMillis();
            System.out.println("query costs :" + (endTimemills-startTimemills) + "ms");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

}
