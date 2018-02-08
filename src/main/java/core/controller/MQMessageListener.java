package core.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by ssc on 2018/1/28.
 */
public class MQMessageListener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        System.out.println("========监听【QueueListenter】消息======");
        String str;
        try
        {
            str = new String(message.getBody(), "UTF-8");
            TimeUnit.SECONDS.sleep(3); //do something
            System.out.println("=====获取消息:"+str);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
