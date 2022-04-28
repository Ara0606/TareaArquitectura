package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author jrojas
 */
@Configuration
public class RabbitMqConfig {
    //Direct
    public static final String QUEUE = "test_queue";
    public static final String EXCHANGE = "test_exchange";
    public static final String ROUTING_KEY = "test_routing_key";
    //Fanout
    public static final String QUEUEFANOUT = "fanout_queque";
    public static final String EXCHANGEFANOUTE = "exchange_fanout";
    //Topic
    public static final String QUEUETOPIC = "TOPIC_queue";
    public static final String EXCHANGETOPIC = "topic_exchange";
    public static final String ROUTING_KEYTOPIC = "queue.topic.*";
    public static final String ROUTING_KEYTOPIC3 = "queue.teacher.*";
    public static final String ROUTING_KEYTOPIC4 = "queue.estudiantes.*";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    //Fanout
    @Bean
    public Queue queueFanout() {
        return new Queue(QUEUEFANOUT);
    }

    @Bean
    public FanoutExchange exchangeFanout() {
        return new FanoutExchange(EXCHANGEFANOUTE);
    }

    @Bean
    public Binding financeBinding1(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding financeBinding2(Queue queueFanout, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueFanout).to(fanoutExchange);
    }
//Topic
    @Bean
    Queue allQueue() {
        return new Queue(QUEUETOPIC);
    }
    @Bean
    Queue teacher() {
        return new Queue("teacher");
    }
    @Bean
    Queue estudiantes() {
        return new Queue("estudiantes");
    }
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGETOPIC);
    }
    @Bean
    Binding teacherBinding(Queue teacher,TopicExchange topicExchange  ) {
        return BindingBuilder.bind(teacher).to(topicExchange).with("queue.teacher.*");
   }
    @Bean
    Binding estudianteBinding(Queue estudiantes,TopicExchange topicExchange  ) {
        return BindingBuilder.bind(estudiantes).to(topicExchange).with("queue.estudiantes.* uniforme .*");
    }

    @Bean
    Binding allBinding(Queue allQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(allQueue).to(topicExchange).with(ROUTING_KEYTOPIC);
    }
}
