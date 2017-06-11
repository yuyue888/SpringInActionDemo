package core.controller;

import core.entity.RestTest;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ssc on 2017/6/4.
 */
@RestController
@RequestMapping("/")
public class TestController {
    @RequestMapping(value= "/test/{test_name}",method = RequestMethod.GET)
    public String getTestName(@PathVariable("test_name") String name){
        return name;
    }

    @RequestMapping(value="/test/json",method = RequestMethod.GET)
    public RestTest getRest(){
        RestTest restTest = new RestTest();
        restTest.setAge(20);
        restTest.setName("王二狗");
        restTest.setSexType(RestTest.SEX.MAN);
        return restTest;
    }

    @RequestMapping(value="/test/json",method = RequestMethod.POST)
    public RestTest postTest(@RequestBody RestTest restTest){
        return restTest;
    }
}
