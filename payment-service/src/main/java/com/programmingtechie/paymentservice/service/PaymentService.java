package com.programmingtechie.paymentservice.service;

import com.programmingtechie.paymentservice.dto.PaymentRequest;
import com.programmingtechie.paymentservice.dto.PaymentResponse;
import com.programmingtechie.paymentservice.model.Payment;
import com.programmingtechie.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setOrderNumber(paymentRequest.getOrderNumber());
        payment.setTotalPrice(paymentRequest.getTotalPrice());

        return mapToPaymentResponse(paymentRepository.save(payment));
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .createdAt(payment.getCreatedAt())
                .orderNumber(payment.getOrderNumber())
                .totalPrice(payment.getTotalPrice())
                .build();
    }
}
