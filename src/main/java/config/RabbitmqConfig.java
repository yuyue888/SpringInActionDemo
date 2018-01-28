package config;

import core.controller.MQMessageListener;
import org.springframework.amqp.core.*;
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

    @Bean
    public Queue queue(){
        return new Queue("myqueue");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("myqueue").noargs();
    }

    @Bean
    public Exchange directExchange(){
        ExchangeBuilder exchangeBuilder = new ExchangeBuilder("amq.direct","direct");
        return exchangeBuilder.build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate();
        template.setExchange("amp.direct");
        template.setQueue("myqueue");
        template.setRoutingKey("myqueue");
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setChannelTransacted(true);
        container.setQueueNames("myqueue");
        container.setMessageListener(new MQMessageListener());
        return container;
    }

}
