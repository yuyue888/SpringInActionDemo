package core.support;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class DefaultExceptionResolver implements ExceptionResolver, Ordered {
    @Override
    public boolean process(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
        ErrorMessage message = new ErrorMessage();
        message.setHostId(request.getRemoteHost());
        message.setRequestId(request.getRequestedSessionId());
        message.setServerTime(new Date());
        message.setMessage(throwable.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSON(message));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
