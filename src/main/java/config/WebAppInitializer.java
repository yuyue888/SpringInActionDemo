package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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
        super.onStartup(servletContext);
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter",new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "utf-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        super.customizeRegistration(registration);
        registration.addMapping("/");
        registration.setMultipartConfig(new MultipartConfigElement("tmp/SAD/uploads", 20 * 1024 * 1024, 40 * 1024 * 1024, 0));
    }

    @Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        return super.registerServletFilter(servletContext, filter);
    }

    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        super.registerContextLoaderListener(servletContext);
    }
}
