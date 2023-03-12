package com.programmingtechie.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.programmingtechie.paymentservice.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
