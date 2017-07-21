package core.support.Resp;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ssc on 2017/7/21 0021.
 */
@ControllerAdvice
public class ExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public ExceptionResp handleException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        ExceptionResp resp = new ExceptionResp();
        resp.setCode("system");
        resp.setMessage(e.getMessage());
        resp.setServerTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        resp.setHostId(request.getServerName());
        resp.setDetail(getExceptionDetail(e));
        resp.setRequestId(UUID.randomUUID().toString());
        return resp;
    }

    private String getExceptionDetail(Throwable e) {
        StringBuilder buffer = new StringBuilder();
        StackTraceElement[] elements = e.getStackTrace();
        buffer.append("Stack trace:");
        for (StackTraceElement element : elements) {
            buffer.append(element.toString());
        }
        return buffer.toString();
    }
}
