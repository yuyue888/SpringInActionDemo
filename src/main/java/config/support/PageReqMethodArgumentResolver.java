package config.support;

import core.support.Req.PageReq;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 分页参数转换器
 * Created by ssc on 2017/7/17 0017.
 */
public class PageReqMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final static int DEFAULT_PAGE = 1;
    private final static int DEFAULT_SIZE = 10;

    private final static String PAGE_NAME = "page";
    private final static String PAGE_SIZE = "size";

    private String pageParamName = PAGE_NAME;
    private String sizeParamName = PAGE_SIZE;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == PageReq.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String page = nativeWebRequest.getParameter(pageParamName);
        String size = nativeWebRequest.getParameter(sizeParamName);
        int pageNum = DEFAULT_PAGE;
        int sizeNum = DEFAULT_SIZE;
        if (page != null) {
            try {
                pageNum = Integer.parseInt(page);
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException:" + page);
            }
        }
        if (size != null) {
            try {
                sizeNum = Integer.parseInt(size);
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException:" + size);
            }
        }
        return new PageReq(pageNum > 0 ? pageNum : DEFAULT_PAGE, sizeNum > 0 ? sizeNum : DEFAULT_SIZE);
    }

    public String getPageParamName() {
        return pageParamName;
    }

    public void setPageParamName(String pageParamName) {
        this.pageParamName = pageParamName;
    }

    public String getSizeParamName() {
        return sizeParamName;
    }

    public void setSizeParamName(String sizeParamName) {
        this.sizeParamName = sizeParamName;
    }
}
