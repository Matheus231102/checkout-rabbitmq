package com.checkout.rbmq.mensageria.dtos.utils;

import com.checkout.rbmq.mensageria.constants.RabbitMQHeaders;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;

public class LogContextHelper {

    private LogContextHelper() {}

    public static String extractCorrelationId(Message message) {

        Object value = message.getMessageProperties()
                .getHeaders()
                .get(RabbitMQHeaders.CORRELATION_ID_KEY);

        if (value instanceof String correlationId) {
            return correlationId;
        }

        return RabbitMQHeaders.NO_CORRELATION_ID_VALUE;
    }

    public static void setupContext(Message message, String domain) {
        Object value = message.getMessageProperties()
                .getHeaders()
                .get(RabbitMQHeaders.CORRELATION_ID_KEY);

        String correlationId = (value instanceof String) ? (String) value : RabbitMQHeaders.NO_CORRELATION_ID_VALUE;

        MDC.put(RabbitMQHeaders.CORRELATION_ID_KEY, correlationId);
        MDC.put("domain_id", domain);
        MDC.put("queue", message.getMessageProperties().getConsumerQueue());
    }

    public static void clear() {
        MDC.clear();
    }

}
