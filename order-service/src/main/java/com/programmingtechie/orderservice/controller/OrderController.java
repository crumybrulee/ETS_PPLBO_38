package com.programmingtechie.orderservice.controller;

import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/order")
// @RequiredArgsConstructor
// public class OrderController {
//     private final OrderService orderService;
//     @PostMapping
//     @ResponseStatus(HttpStatus.CREATED)
//     public String placeOrder(@RequestBody OrderRequest orderRequest) {
//         orderService.placeOrder(orderRequest);
//         return "Order placed succesfully";
//     }


// }

@RestController
public class OrderController {
   @Autowired
   private OrderService orderService;

   @PostMapping("/orders")
   public ResponseEntity<?> createOrder(@RequestBody Order order) {
       orderService.createOrder(order);
       return ResponseEntity.ok("Pesanan telah berhasil dibuat");
   }

   public void createOrder(Order order) {
       orderRepository.save(order);
   }
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        orderService.updateOrder(id, order);
        return ResponseEntity.ok("Pesanan telah berhasil diupdate");
    }

    public void updateOrder(Long id, Order order) {
        Order order1 = orderRepository.findById(id).get();
        order1.setCustomerName(order.getCustomerName());
        order1.setProductName(order.getProductName());
        order1.setQuantity(order.getQuantity());
        order1.setTotalPrice(order.getTotalPrice());
        order1.setOrderDate(order.getOrderDate());
        orderRepository.save(order1);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Pesanan telah berhasil dihapus");
    }
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
