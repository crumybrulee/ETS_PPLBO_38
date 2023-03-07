package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.*;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// @Service
// @RequiredArgsConstructor
// @Transactional
// public class OrderService {
//     private final OrderRepository orderRepository;
//     private final WebClient.Builder webClient;
//     public void placeOrder(OrderRequest orderRequest) {
//         Order order = new Order();
//         order.setOrderNumber(UUID.randomUUID().toString());
//         List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
//                 .stream()
//                 .map(orderLineItemsDto -> parseDto(orderLineItemsDto))
//                 .toList();

//         order.setOrderLineItemsList(orderLineItemsList);

//         List<String> skuCodes = order.getOrderLineItemsList()
//                 .stream()
//                 .map(orderLineItems -> orderLineItems.getSkuCode())
//                 .toList();

//         // call inventory service and place order if product is instock
//         InventoryResponse[] inventoryResponses = webClient.build().get()
//                 .uri("http://inventory-service/api/inventory",
//                         uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                 .retrieve()
//                 .bodyToMono(InventoryResponse[].class)
//                 .block();

//         boolean allProductInStock = Arrays.stream(inventoryResponses).
//                 allMatch(inventoryResponse -> inventoryResponse.isInStock());

//         if (allProductInStock) {
//             Order createdOrder = orderRepository.save(order);
//             createPayment(createdOrder.getOrderNumber(), orderLineItemsList);

// //            List<OrderLineItemsUpdate>

//         } else {
//             throw new IllegalArgumentException("Product is not in stock");
//         }
//     }

//     public void createPayment(String orderNumber, List<OrderLineItems> orderLineItems) {
//         BigDecimal total_price = orderLineItems.stream()
//                 .map(orderLineItem -> orderLineItem.getPrice().multiply(BigDecimal.valueOf(orderLineItem.getQuantity())))
//                 .reduce(BigDecimal.valueOf(0), (a, b) -> a.add(b));


//         PaymentRequest paymentRequest = PaymentRequest.builder()
//                 .orderNumber(orderNumber)
//                 .totalPrice(total_price)
//                 .build();

//         webClient.build().post()
//                 .uri("http://payment-service/api/payment")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(Mono.just(paymentRequest), PaymentRequest.class)
//                 .retrieve()
//                 .bodyToMono(PaymentResponse.class)
//                 .block();
//     }

//     private OrderLineItems parseDto(OrderLineItemsDto orderLineItemsDto) {
//         OrderLineItems orderLineItems = new OrderLineItems();
//         orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
//         orderLineItems.setPrice(orderLineItemsDto.getPrice());
//         orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

//         return orderLineItems;
//     }
// }

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setCustomerName(orderRequest.getCustomerName());
        order.setProductName(orderRequest.getProductName());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setOrderDate(orderRequest.getOrderDate());
        Order createdOrder = orderRepository.save(order);
        createPayment(createdOrder.getOrderNumber(), order);
    }

    public void createPayment(String orderNumber, Order order) {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderNumber(orderNumber)
                .totalPrice(order.getTotalPrice())
                .build();

        webClient.build().post()
                .uri("http://payment-service/api/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(paymentRequest), PaymentRequest.class)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();
    }

    public void updateOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(orderRequest.getOrderNumber());
        order.setCustomerName(orderRequest.getCustomerName());
        order.setProductName(orderRequest.getProductName());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setOrderDate(orderRequest.getOrderDate());
        Order createdOrder = orderRepository.save(order);
        createPayment(createdOrder.getOrderNumber(), order);
    }

    public void deleteOrder(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        orderRepository.delete(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
    }
}
