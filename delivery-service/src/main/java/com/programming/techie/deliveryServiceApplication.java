package com.programming.techie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
class deliveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(deliveryServiceApplication.class, args);
    }

    @KafkaListener(topics = "deliveryTopic")
    public void handledelivery(OrderPlacedEvent orderPlacedEvent) {
        // send out an email delivery
        log.info("Received delivery for Order - {}", orderPlacedEvent.getOrderNumber());
    }
}
