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
public class LogisticConsumer {

    private static final Logger log = LoggerFactory.getLogger(LogisticConsumer.class);

    @RabbitListener(queues = RabbitMQConstants.QUEUE_LOGISTIC)
    public void consume(OrderEvent order, Message message) {
        setupCorrelationId(message);
        try {
            Thread.sleep(280);
            log.info("[LOGISTIC] Preparando envio do pedido {}", order.getOrderId());
        } catch (Exception exception) {
            log.info("[LOGISTIC] erro no preparado do pedido ({})", order.getOrderId());
            // sem tratamento, somente para teste
        } finally {
            clearCorrelationId();
        }
    }
}