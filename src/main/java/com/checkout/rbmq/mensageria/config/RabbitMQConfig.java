package com.checkout.rbmq.mensageria.config;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;


@Configuration
public class RabbitMQConfig {


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
