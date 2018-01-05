package core.support;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionFilter extends OncePerRequestFilter {

    ExceptionResolver resolver = new DefaultExceptionResolver();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            System.out.println("do exception filter");
            resolver.process(e.getCause(),request,response);
            e.printStackTrace();
        }
    }
}
