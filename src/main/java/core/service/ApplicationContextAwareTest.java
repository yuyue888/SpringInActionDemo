package core.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 实现ApplicationContextAware接口的Beans在会自动调用setApplicationContext方法
 * Created by ssc on 2017/7/18 0018.
 */
public class ApplicationContextAwareTest implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
