package com.checkout.rbmq.mensageria.dtos.utils;

import com.checkout.rbmq.mensageria.constants.RabbitMQHeaders;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;

public class RabbitMQUtils {

    private RabbitMQUtils() {}

    public static String extractCorrelationId(Message message) {

        Object value = message.getMessageProperties()
                .getHeaders()
                .get(RabbitMQHeaders.CORRELATION_ID_KEY);

        if (value instanceof String correlationId) {
            return correlationId;
        }

        return RabbitMQHeaders.NO_CORRELATION_ID_VALUE;
    }

    public static String setupCorrelationId(Message message) {
        Object value = message.getMessageProperties()
                .getHeaders()
                .get(RabbitMQHeaders.CORRELATION_ID_KEY);

        String correlationId;

        if (value instanceof String) {
            correlationId = (String) value;
        } else {
            correlationId = RabbitMQHeaders.NO_CORRELATION_ID_VALUE;
        }

        MDC.put(RabbitMQHeaders.CORRELATION_ID_KEY, correlationId);
        return correlationId;
    }


    public static void clearCorrelationId() {
        MDC.remove(RabbitMQHeaders.CORRELATION_ID_KEY);
    }

}
