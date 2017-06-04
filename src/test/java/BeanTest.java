import config.WebConfig;
import core.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ssc on 2017/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
public class BeanTest {
    @Autowired
    private TestService testService;

    @Test
    public void test(){
        System.out.println("bean name:"+testService.getTestBean());
    }
}
