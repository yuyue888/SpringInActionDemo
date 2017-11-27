/**
 * @CoypRight:nd.com (c) 2017 All Rights Reserved
 * @date 2017年4月1日 下午5:03:54
 * @version V1.0
 */
package config.support.redis;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;

/**
 * <p>
 * </p>
 *
 */
@Aspect
@Component
public class RedisCacheAop {
    @Autowired
    private ResourcePlatformJedis resourcePlatformJedis;

    private static Logger logger = Logger.getLogger(RedisCacheAop.class);

    /**
     * Pointcut 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(* core..*(..)) && @annotation(config.support.redis.RedisCache)")
    private void aspectjMethod() {
    }

    ;

    /**
     * Before 在核心业务执行前执行，不能阻止核心业务的调用。
     *
     * @param joinPoint
     */
    @Before("aspectjMethod()")
    public void beforeAdvice(JoinPoint joinPoint) {
    }

    /**
     * After 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
     *
     * @param joinPoint
     */
    @After(value = "aspectjMethod()")
    public void afterAdvice(JoinPoint joinPoint) {
    }

    /**
     * Around 手动控制调用核心业务逻辑，以及调用前和调用后的处理, 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 执行完AfterAdvice，再转到ThrowingAdvice
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        SimpleKeyGenerator simpleKey = new SimpleKeyGenerator();
        final String key = simpleKey.generate(pjp.getTarget(), signature.getMethod(), pjp.getArgs()).toString();
        Object retVal = resourcePlatformJedis.get(key);
        boolean isExpire = resourcePlatformJedis.isExpire(key);
        if (retVal == null || isExpire ) {
            synchronized (key.intern()) {
                retVal = resourcePlatformJedis.get(key);
                isExpire = resourcePlatformJedis.isExpire(key);
                if (retVal == null || isExpire ) {
                    retVal = pjp.proceed();
                    resourcePlatformJedis.set(key, retVal);
                    logger.info("add to redis cache");
                }
            }
        }else {
            logger.info("get data from redis cache");
        }
        return retVal;
    }

    /**
     * AfterReturning 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     *
     * @param joinPoint
     */
    @AfterReturning(value = "aspectjMethod()", returning = "retVal")
    public void afterReturningAdvice(JoinPoint joinPoint, String retVal) {
    }

    /**
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息 注意：执行顺序在Around Advice之后
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "aspectjMethod()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
    }
}
