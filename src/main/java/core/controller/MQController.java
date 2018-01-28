package core.controller;

import core.service.AlertService;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by ssc on 2018/1/28.
 */
@RestController
public class MQController {
    @Autowired
    private AlertService alertService;

    @RequestMapping(value = "/sendmsg",method = RequestMethod.POST)
    public void sendMsg(@RequestBody String msg){
        alertService.sendObject(msg);
    }

    @RequestMapping(value = "/receivemsg",method = RequestMethod.GET)
    public String receiveMsg(){
        return alertService.receiveMsg();
    }

    @RequestMapping(value = "/receiveobj",method = RequestMethod.GET)
    public Object receiveObj(){
        return alertService.receiveObj();
    }
}
