package com.programmingtechie.inventoryservice.service;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.List;

public class DeliveryService {
    
    private List<Delivery> deliveries;
    private static DeliveryService deliveryService;

    // singleton pattern
    private DeliveryService() {
        this.deliveries = new ArrayList<>();
    }

    public static DeliveryService getInstance() {
        if (deliveryService == null) {
            deliveryService = new DeliveryService();
        }
        return deliveryService;
    }

    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Delivery> getDeliveriesByBranch(String branch) {
        List<Delivery> deliveriesByBranch = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            if (delivery.getBranch().equals(branch)) {
                deliveriesByBranch.add(delivery);
            }
        }
        return deliveriesByBranch;
    }

    public List<Delivery> getDeliveriesByDate(String date) {
        List<Delivery> deliveriesByDate = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            if (delivery.getDate().equals(date)) {
                deliveriesByDate.add(delivery);
            }
        }
        return deliveriesByDate;
    }
}

