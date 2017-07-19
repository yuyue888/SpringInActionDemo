package core.controller;

import core.entity.RestTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ssc on 2017/7/19 0019.
 */
@Controller
public class ReturnValueHanderTestController {
    @RequestMapping(value = "/test/return", method = RequestMethod.GET)
    public RestTest getRest() {
        RestTest restTest = new RestTest();
        restTest.setAge(20);
        restTest.setName("王二狗");
        restTest.setSexType(RestTest.SEX.MAN);
        return restTest;
    }
}
