package com.programmingtechie.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/")
    public ResponseEntity<Delivery> addDelivery(@RequestBody Delivery delivery) {
        deliveryService.addDelivery(delivery);
        return new ResponseEntity<>(delivery, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<List<Delivery>> getDeliveriesByBranch(@PathVariable String branch) {
        List<Delivery> deliveriesByBranch = deliveryService.getDeliveriesByBranch(branch);
        return new ResponseEntity<>(deliveriesByBranch, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Delivery>> getDeliveriesByDate(@PathVariable String date) {
        List<Delivery> deliveriesByDate = deliveryService.getDeliveriesByDate(date);
        return new ResponseEntity<>(deliveriesByDate, HttpStatus.OK);
    }

}

