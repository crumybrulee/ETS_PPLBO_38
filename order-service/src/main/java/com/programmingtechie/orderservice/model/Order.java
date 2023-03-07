package com.programmingtechie.orderservice.model;


import com.programmingtechie.orderservice.repository.OrderRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// @Entity
// @Table(name = "t_orders")
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// public class Order {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private  Long id;
//     private  String orderNumber;
//     @OneToMany(cascade = CascadeType.ALL)
//     private List<OrderLineItems> orderLineItemsList;
// }

@Getter
@Setter
@Entity
public class Order {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   private String customerName;
   private String productName;
   private int quantity;
   private double totalPrice;
   private LocalDateTime orderDate;

   // getters and setters
}
