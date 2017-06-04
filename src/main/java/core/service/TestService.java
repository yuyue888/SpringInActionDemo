package core.service;

import config.WebConfig;
import core.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by ssc on 2017/6/3.
 */
@Component
public class TestService {
    @Autowired
    private Test test;

    public String getTestBean(){
        return test.getTestName();
    }
}
