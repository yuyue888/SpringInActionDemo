package core.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionResolver {
    boolean process(Throwable throwable, HttpServletRequest request, HttpServletResponse response);
}
