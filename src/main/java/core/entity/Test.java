package core.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by ssc on 2017/6/3.
 */
@Component
public class Test {
    private String testName ="Test Bean";

    @Bean
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
