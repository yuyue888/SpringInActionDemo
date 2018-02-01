package config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ssc on 2018/1/20.
 */
@Configuration
@PropertySource(value = {"classpath:/rabbitmq.properties"})
public class RabbitmqConfig {
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private int port;
    @Value("${application.name}")
    private String name;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String passaword;
    @Value("${rabbitmq.virtualhost}")
    private String virtualhost;


    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(passaword);
        connectionFactory.setVirtualHost(virtualhost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

//    @Bean
//    public Queue queue(){
//        return new Queue("queue");
//    }

//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue()).to(directExchange()).with("myqueue").noargs();
//    }

    @Bean
    public Exchange directExchange(){
        ExchangeBuilder exchangeBuilder = new ExchangeBuilder("amq.direct","direct");
        return exchangeBuilder.build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate();
//        template.setExchange("amp.direct");
//        template.setQueue("myqueue");
        template.setRoutingKey("queue");
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setChannelTransacted(true);
        container.setQueueNames("queue");
        container.setMessageListener(exampleListener());
        return container;
    }

    @Bean
    public MessageListener exampleListener() {
        return message -> {
            //amqpReceiver.onMessage(message);
            System.out.print("接收消息：" + new String(message.getBody()));
        };
    }

}
