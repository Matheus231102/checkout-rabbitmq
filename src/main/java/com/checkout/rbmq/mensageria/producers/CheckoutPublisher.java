package com.checkout.rbmq.mensageria.producers;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import com.checkout.rbmq.mensageria.constants.RabbitMQHeaders;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CheckoutPublisher {

    private final RabbitTemplate rabbitTemplate;

    public CheckoutPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJson(String jsonPayload) {
    String correlationId = UUID.randomUUID().toString();
    
    MessagePostProcessor processor = message -> {
        message.getMessageProperties().setHeader(RabbitMQHeaders.CORRELATION_ID_KEY, correlationId);
        message.getMessageProperties().setContentType("application/json");
        return message;
    };

    rabbitTemplate.convertAndSend(
            RabbitMQConstants.EXCHANGE_ORDER,
            RabbitMQConstants.RK_ORDER_PAID,
            jsonPayload,
            processor
    );
}
}
