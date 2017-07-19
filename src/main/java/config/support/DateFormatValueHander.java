package config.support;

import core.entity.RestTest;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;

/**
 * HandlerMethodReturnValueHandler:处理返回对象的接口
 * Created by ssc on 2017/7/19 0019.
 */
public class DateFormatValueHander implements HandlerMethodReturnValueHandler {
    /**
     * 判断true时会调用该handler
     *
     * @param returnType
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getParameterType() == RestTest.class;
    }


    /**
     * 自定义行为处理返回对象
     * 需要去掉Controller中的@ResponseBody注解才生效
     *
     * @param returnValue
     * @param returnType
     * @param mavContainer
     * @param webRequest
     * @throws Exception
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);//此次请求是否是由handler自己控制的，true表示本方法会响应请求。
        RestTest restTest = (RestTest) returnValue;
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write("restTest:" );
    }
}
