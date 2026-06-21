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
public class StockConsumer {

    private final ObjectMapper objectMapper;

    public StockConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_STOCK)
    public void consume(String payload, Message message) {
        setupContext(message, "stock-consumer");

        try {
            OrderEvent order = objectMapper.readValue(payload, OrderEvent.class);

            log.info("[STOCK] Pedido recebido: {}", order.getOrderId());
            order.getItems().forEach(item -> log.debug("[STOCK] Produto: {} | Qtd: {}", item.getProductId(), item.getQuantity()));

            processarMensagem();

            log.info("[STOCK] Processado com sucesso: {}", order.getOrderId());
        } catch (Exception e) {
            log.error("[STOCK] Erro no pedido", e);
        } finally {
            clear();
        }
    }

    private void processarMensagem() {
        try {
            Thread.sleep(30);
        } catch (Exception e) {
        }
    }

}
