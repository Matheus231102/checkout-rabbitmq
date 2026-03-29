package com.checkout.rbmq.mensageria.producers;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import com.checkout.rbmq.mensageria.constants.RabbitMQHeaders;
import com.checkout.rbmq.mensageria.dtos.OrderEvent;
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

    public void sendEvent(OrderEvent orderEvent) {
        String correlationId = UUID.randomUUID().toString();
        /**
         * colocando correlationId para poder rastrear melhor a ação que está sendo executada
         */
        MessagePostProcessor processor = message -> {
            message.getMessageProperties().setHeader(RabbitMQHeaders.CORRELATION_ID_KEY, correlationId);
            return message;
        };

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE_ORDER,
                RabbitMQConstants.RK_ORDER_PAID,
                orderEvent,
                processor
        );

    }
}
