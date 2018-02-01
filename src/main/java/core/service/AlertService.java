package core.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ssc on 2018/1/28.
 */
@Service
public class AlertService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsgAndAlert(String msg){
        Message message = new Message(msg.getBytes(),new MessageProperties());
        rabbitTemplate.send("amp.direct","queue" ,message);
    }

    public void sendObject(Object o){
        rabbitTemplate.convertAndSend(o);

    }

    public String receiveMsg(){
        Message message = rabbitTemplate.receive("queue");
        if(message == null ){
            return "";
        }
        return new String(message.getBody());
    }

    public Object receiveObj(){
        return rabbitTemplate.receiveAndConvert();
    }
}
