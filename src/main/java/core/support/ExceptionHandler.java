package core.support;

/**
 * Created by ssc on 2017/6/18 0018.
 */

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 统一异常处理器。
 * </p>
 */
@ControllerAdvice
public class ExceptionHandler implements InitializingBean, ApplicationContextAware {
    /**
     * Spring ApplicationContext
     */
    private ApplicationContext applicationContext;

    /**
     * 异常转换器列表
     */
    private List<ExceptionTranslator> translators;

    private Map<Class<? extends Throwable>, ExceptionTranslator> exceptionCache = new HashMap<Class<? extends Throwable>, ExceptionTranslator>();

//    @Autowired
//    private ConversionService conversionService;

    private final ExceptionDefinition defaultProgramExceptionDefinition = new ExceptionDefinition(500,
            "SYS/INTERNAL_SERVER_ERROR", "{message}", ExceptionType.PROGRAM);

    private final ExceptionDefinition defaultBusinessExceptionDefinition = new ExceptionDefinition(500,
            "BUS/INTERNAL_SERVER_ERROR", "{message}", ExceptionType.BUSINESS);

    @org.springframework.web.bind.annotation.ExceptionHandler(Throwable.class)
    @ResponseBody
    public ExceptionResponse handleException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        ExceptionDefinition exceptionDefinition = null;
        ExceptionTranslator translator = exceptionCache.get(e.getClass());
        if (translator != null) {
            exceptionDefinition = translator.translate(e);
        } else if (translators != null && !translators.isEmpty()) {
            for (ExceptionTranslator t : translators) {
                exceptionDefinition = t.translate(e);
                if (exceptionDefinition != null) {
                    exceptionCache.put(e.getClass(), t);
                    break;
                }
            }
        }
        if (exceptionDefinition == null) {
            e.printStackTrace();
            if (e instanceof BusinessException) {
                String code;
                String className = e.getClass().getSimpleName();
                if (className == null) {
                    code = defaultBusinessExceptionDefinition.getCode();
                } else {
                    if (className.endsWith("Exception")) {
                        className = className.substring(0, className.length() - 9);
                    }
                    code = "BUS/" + className.replaceAll("(.)([A-Z])", "$1_$2").toUpperCase();
                }
                exceptionDefinition = new ExceptionDefinition(defaultBusinessExceptionDefinition.getHttpStatus(), code,
                        defaultBusinessExceptionDefinition.getMessage(), defaultBusinessExceptionDefinition.getType());
            } else {
                exceptionDefinition = defaultProgramExceptionDefinition;
            }
        }
        String message = parseMessage(exceptionDefinition.getMessage(), e);
        String requestId = UUID.randomUUID().toString();
        String hostId = request.getServerName();
        ExceptionResponse exceptionResponse = new ExceptionResponse(exceptionDefinition.getCode(), message, hostId,
                requestId, exceptionDefinition.getType());
        int httpStatus = exceptionDefinition.getHttpStatus();
        response.setStatus(httpStatus);
        return exceptionResponse;
    }

    private String parseMessage(String message, Throwable e) {
        if (message == null) {
            return "";
        }
        String regex = "\\{[^\\}]+\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            int start = matcher.start(), end = matcher.end();
            sb.append(message.substring(i, start));
            String v = matcher.group();
            String varName = v.substring(1, v.length() - 1);
            try {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(e.getClass(), varName);
                Object result = propertyDescriptor.getReadMethod().invoke(e);
//                if (result != null && !(result instanceof String)) {
//                    try {
//                        result = conversionService.convert(result, String.class);
//                    } catch (ConverterNotFoundException cnfe) {
//                        result = result.toString();
//                    }
//                }
                if (result != null) {
                    sb.append(result);
                }
            } catch (Exception pe) {
                return "<<解析失败的消息>>";
            }
            i = end;
        }
        sb.append(message.substring(i));
        return sb.toString();
    }

    /**
     * 从 Spring 中查找所有实现了{@link ExceptionTranslator}接口的对象。
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, ExceptionTranslator> map = this.applicationContext.getBeansOfType(ExceptionTranslator.class);
        translators = new ArrayList<>(map.values());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
