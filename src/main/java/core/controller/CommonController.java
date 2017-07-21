package core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ssc on 2017/7/20 0020.
 */
@Controller
public class CommonController {
//    @RequestMapping(value = "*", method = RequestMethod.GET)
//    public String notFound() {
//        return "/404";
//    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }
}
