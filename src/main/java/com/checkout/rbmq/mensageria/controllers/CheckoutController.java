package com.checkout.rbmq.mensageria.controllers;


import com.checkout.rbmq.mensageria.dtos.OrderEvent;
import com.checkout.rbmq.mensageria.producers.CheckoutPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutPublisher publisher;
    private final ObjectMapper objectMapper;

    public CheckoutController(CheckoutPublisher publisher, ObjectMapper objectMapper) {
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }

//    @PostMapping
//    public String createOrder(@RequestBody OrderEvent order) {
//        publisher.sendEvent(order);
//        return "Pedido enviado!";
//    }

    @PostMapping("/fake")
    public String gerarPedidoFake() {
        OrderEvent fakeOrder = OrderEvent.fake();
        try {
            String jsonOrder = objectMapper.writeValueAsString(fakeOrder);
            log.debug("gerando fakeOrder: {}", jsonOrder);

            System.out.println("Enviando JSON: " + jsonOrder);
            publisher.sendJson(jsonOrder);
            return "Pedido fake enviado: " + fakeOrder.getOrderId();
        } catch (Exception e) {
            return "Erro ao gerar JSON: " + e.getMessage();
        }
    }

    @PostMapping("/stress")
    public String stressTest(@RequestParam(defaultValue = "100") int quantidade) {
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < quantidade; i++) {
            OrderEvent fakeOrder = OrderEvent.fake();
            String jsonOrder = objectMapper.writeValueAsString(fakeOrder);
            publisher.sendJson(jsonOrder);
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
                    OrderEvent fakeOrder = OrderEvent.fake();
                    String jsonOrder = objectMapper.writeValueAsString(fakeOrder);
                    publisher.sendJson(jsonOrder);
                });

        long fim = System.currentTimeMillis();
        return "Enviados " + quantidade + " pedidos em paralelo em " + (fim - inicio) + "ms";
    }

}
