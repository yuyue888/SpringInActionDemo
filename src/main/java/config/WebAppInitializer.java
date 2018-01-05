package config;

import core.support.ExceptionFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.*;
import java.util.EnumSet;

/**
 *
 * Created by ssc on 2017/6/4.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 在Spring里可以获取到 ApplicationContext , 在RootConfig中配置
     * @return
     */
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    /**
     * 每一个 DispatcherServlet 都拥有自己的 WebApplicationContext，这个 WebApplicationContext 继承了根 WebApplicationContext 定义的所有 bean.
     * @return
     */
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter",new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "utf-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

        registerServletFilter(servletContext,new ExceptionFilter());

        super.onStartup(servletContext);
    }

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        String servletName = getServletName();
        WebApplicationContext servletAppContext = createServletApplicationContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
        // throw NoHandlerFoundException to Controller
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
        customizeRegistration(registration);
    }

//    @Override
//    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
////        registration.addMapping("/");
////        registration.setMultipartConfig(new MultipartConfigElement("tmp/SAD/uploads", 20 * 1024 * 1024, 40 * 1024 * 1024, 0));
//        super.customizeRegistration(registration);
//    }

    @Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        return super.registerServletFilter(servletContext, filter);
    }

    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        super.registerContextLoaderListener(servletContext);
//        servletContext.addListener(new Log4jConfigListener());
    }
}
