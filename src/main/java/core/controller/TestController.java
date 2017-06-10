package core.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
