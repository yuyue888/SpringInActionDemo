package core.service;

import config.WebConfig;
import core.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

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

//    @Scheduled(fixedRate = 5000)
//    public void ScheduleTest(){
//        System.out.println("------当前时间："+new Date()+"------");
//    }
}
