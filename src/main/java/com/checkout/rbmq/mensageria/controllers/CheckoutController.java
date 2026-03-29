package com.checkout.rbmq.mensageria.controllers;


import com.checkout.rbmq.mensageria.dtos.OrderEvent;
import com.checkout.rbmq.mensageria.producers.CheckoutPublisher;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutPublisher publisher;
    
    public CheckoutController(CheckoutPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderEvent order) {
        publisher.sendEvent(order);
        return "Pedido enviado!";
    }

    @PostMapping("/fake")
    public String gerarPedidoFake() {
        OrderEvent fakeOrder = OrderEvent.fake();
        publisher.sendEvent(fakeOrder);
        return "Pedido fake enviado: " + fakeOrder.getOrderId();
    }

    @PostMapping("/stress")
    public String stressTest(@RequestParam(defaultValue = "100") int quantidade) {
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < quantidade; i++) {
            OrderEvent order = OrderEvent.fake();
            publisher.sendEvent(order);
        }
        long fim = System.currentTimeMillis();

        return "Enviados " + quantidade + " pedidos em " + (fim - inicio) + "ms";
    }

    @PostMapping("/stress-parallel")
    public String stressParallel(@RequestParam(defaultValue = "500") int quantidade) {
        long inicio = System.currentTimeMillis();
        java.util.stream.IntStream.range(0, quantidade)
                .parallel()
                .forEach(i -> {
                    OrderEvent order = OrderEvent.fake();
                    publisher.sendEvent(order);
                });

        long fim = System.currentTimeMillis();
        return "Enviados " + quantidade + " pedidos em paralelo em " + (fim - inicio) + "ms";
    }

}
