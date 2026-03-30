package com.checkout.rbmq.mensageria.consumers;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import com.checkout.rbmq.mensageria.dtos.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import static com.checkout.rbmq.mensageria.dtos.utils.RabbitMQUtils.clearCorrelationId;
import static com.checkout.rbmq.mensageria.dtos.utils.RabbitMQUtils.setupCorrelationId;

//import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class LogisticConsumer {

    private final ObjectMapper objectMapper;

    public LogisticConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_LOGISTIC)
    public void consume(String payload, Message message) {
        setupCorrelationId(message);
        try {
            OrderEvent order = objectMapper.readValue(payload, OrderEvent.class);
            Thread.sleep(280);
            log.info("[LOGISTIC] Preparando envio do pedido {}", order.getOrderId());
        } catch (Exception exception) {
            log.info("[LOGISTIC] erro no preparado do pedido", exception);
        } finally {
            clearCorrelationId();
        }
    }
}