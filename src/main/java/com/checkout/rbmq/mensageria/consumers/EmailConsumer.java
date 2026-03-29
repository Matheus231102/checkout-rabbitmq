package com.checkout.rbmq.mensageria.consumers;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import com.checkout.rbmq.mensageria.dtos.OrderEvent;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.checkout.rbmq.mensageria.dtos.utils.RabbitMQUtils.clearCorrelationId;
import static com.checkout.rbmq.mensageria.dtos.utils.RabbitMQUtils.setupCorrelationId;

@Component
public class EmailConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    @RabbitListener(queues = RabbitMQConstants.QUEUE_EMAIL)
    public void consume(OrderEvent order, Message message) {
        setupCorrelationId(message);
        log.info("[EMAIL] Pedido recebido: {}", order.getOrderId());
        try {
            Thread.sleep(450);
        } catch (Exception exception) {
            log.error("[EMAIL] Erro ao enviar email para o pedido ({})", order.getOrderId(), exception);
        } finally {
            clearCorrelationId();
        }
        log.info("[EMAIL] Enviando email para {} (pedido: {})", order.getCustomer().getEmail(), order.getOrderId());
    }
}