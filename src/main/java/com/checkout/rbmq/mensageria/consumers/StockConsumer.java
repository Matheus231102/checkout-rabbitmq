package com.checkout.rbmq.mensageria.consumers;

import com.checkout.rbmq.mensageria.constants.RabbitMQConstants;
import com.checkout.rbmq.mensageria.constants.RabbitMQHeaders;
import com.checkout.rbmq.mensageria.dtos.OrderEvent;
import com.checkout.rbmq.mensageria.dtos.utils.RabbitMQUtils;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.checkout.rbmq.mensageria.dtos.utils.RabbitMQUtils.*;

@Component
public class StockConsumer {

    private static final Logger log = LoggerFactory.getLogger(StockConsumer.class);

    @RabbitListener(queues = RabbitMQConstants.QUEUE_STOCK)
    public void consume(OrderEvent order, Message message) {
        setupCorrelationId(message);
        log.info("[STOCK] Pedido recebido: {}", order.getOrderId());

        /**
         * vamos simular com valores diferentes para cada consumer para verificar o
         * o comportamento diferente de enfileiramento das mensagens
         */
        try {
            Thread.sleep(250);
        } catch (Exception e) {
        }

        try {
            order.getItems().forEach(item ->
                    log.debug("[STOCK] Produto: {} | Qtd: {}",
                            item.getProductId(),
                            item.getQuantity())
            );

            log.info("[STOCK] Processado com sucesso: {}", order.getOrderId());
        } catch (Exception e) {
            log.error("[STOCK] Erro no pedido {}", order.getOrderId(), e);
        } finally {
            clearCorrelationId();
        }
    }

}
