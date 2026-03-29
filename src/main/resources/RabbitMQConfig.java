package com.checkout.rbmq.mensageria.configs;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {

    // Em vez de dar 'new Jackson2JsonMessageConverter()',
    // peça ao Spring para injetar o ObjectMapper padrão dele.
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new JacksonJsonMessageConverter(objectMapper);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RabbitMQConstants.EXCHANGE_ORDER);
    }

    @Bean
    public Queue stockQueue() {
        return new Queue(RabbitMQConstants.QUEUE_STOCK);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(RabbitMQConstants.QUEUE_EMAIL);
    }

    @Bean
    public Queue logisticQueue() {
        return new Queue(RabbitMQConstants.QUEUE_LOGISTIC);
    }

    @Bean
    public Binding stockBinding() {
        return BindingBuilder.bind(stockQueue())
                .to(exchange())
                .with(RabbitMQConstants.RK_ORDER_PAID);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(exchange())
                .with(RabbitMQConstants.RK_ORDER_ALL);
    }

    @Bean
    public Binding logisticBinding() {
        return BindingBuilder.bind(logisticQueue())
                .to(exchange())
                .with(RabbitMQConstants.RK_ORDER_PAID);
    }
}