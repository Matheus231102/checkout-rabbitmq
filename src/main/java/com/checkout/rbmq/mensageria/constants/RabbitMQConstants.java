package com.checkout.rbmq.mensageria.constants;

public class RabbitMQConstants {
    private RabbitMQConstants() {}

    // Exchange
    public static final String EXCHANGE_ORDER = "order.topic";

    // Queues
    public static final String QUEUE_STOCK = "order.stock.queue";
    public static final String QUEUE_EMAIL = "order.email.queue";
    public static final String QUEUE_LOGISTIC = "order.logistic.queue";

    // Routing Keys
    public static final String RK_ORDER_PAID = "order.paid";
    public static final String RK_ORDER_ALL = "order.#";

}