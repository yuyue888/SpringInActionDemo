package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.MediaTypeFileExtensionResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Java 配置Spring,替代web.xml
 * Created by ssc on 2017/6/3.
 */
@Configuration
@ComponentScan(basePackages = {"core","config"})
@EnableWebMvc
@EnableScheduling//开启计划任务
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver() {//此解析器只能解析jsp
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    //ContentNegotiatingViewResolver 自身并没有去解析视图，而是将其委派给其他视图解析器，选择指定响应表述返回给客户端
//    @Bean
//    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
//        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
//        ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();
//        contentNegotiationManager.addFileExtensionResolvers();
//
//        // 视图解析器
//        List<ViewResolver> viewResolvers = new ArrayList<>();
//
//        // 内部资源视图解析器.
//        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
//        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
//        internalResourceViewResolver.setSuffix(".jsp");
//
//        viewResolvers.add(new BeanNameViewResolver());
//        viewResolvers.add(internalResourceViewResolver);
//
//        // 默认视图解析器.
//        List<View> defaultViews = new ArrayList<>();
//        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
//        mappingJackson2JsonView.setExtractValueFromSingleKeyModel(true);
//        defaultViews.add(mappingJackson2JsonView);
//
//        contentNegotiatingViewResolver.setViewResolvers(viewResolvers);
//        contentNegotiatingViewResolver.setDefaultViews(defaultViews);
//
//        return contentNegotiatingViewResolver;
//    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        // 配置字符创支持中文
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        // 使用Jackson2Json JSON库 配置Json转换器
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        converters.add(jsonConverter);
        converters.add(converter);
    }


}
