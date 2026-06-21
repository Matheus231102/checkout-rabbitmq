package com.checkout.rbmq.mensageria.consumers;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import com.checkout.rbmq.mensageria.dtos.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import static com.checkout.rbmq.mensageria.dtos.utils.LogContextHelper.*;

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
        setupContext(message, "logistic-consumer");
        try {
            OrderEvent order = objectMapper.readValue(payload, OrderEvent.class);

            processarMensagem(message);

            log.info("preparando envio do pedido {}", order.getOrderId());
        } catch (Exception exception) {
            log.info("erro no preparado do pedido", exception);
        } finally {
            clear();
        }
    }

    private void processarMensagem(Message message) {
        try {
            Thread.sleep(110);
        } catch (Exception e) {
        }
    }
}