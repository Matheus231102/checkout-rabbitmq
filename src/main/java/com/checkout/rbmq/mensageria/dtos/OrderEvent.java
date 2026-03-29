package com.checkout.rbmq.mensageria.dtos;

import com.github.javafaker.Faker;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderEvent {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    private Customer customer;
    private List<Item> items;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    public OrderEvent() {
    }

    public static class Customer {
        private String name;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class Item {
        @JsonProperty("product_id")
        private String productId;
        private Integer quantity;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public enum OrderStatus {
        PAID,
        CANCELLED
    }

    public static OrderEvent fake() {
        Faker faker = new Faker();

        OrderEvent order = new OrderEvent();

        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.PAID);
        order.setCreatedAt(OffsetDateTime.now());

        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setEmail(faker.internet().emailAddress());

        List<Item> items = new ArrayList<>();

        int qtdItens = faker.number().numberBetween(1, 5);

        for (int i = 0; i < qtdItens; i++) {
            Item item = new Item();
            item.setProductId("prod-" + faker.number().numberBetween(1, 100));
            item.setQuantity(faker.number().numberBetween(1, 3));
            items.add(item);
        }

        order.setCustomer(customer);
        order.setItems(items);

        return order;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

}