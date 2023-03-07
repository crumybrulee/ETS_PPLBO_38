package com.programmingtechie.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Timestamp createdAt;
    private String orderNumber;
    private BigDecimal totalPrice;
}
