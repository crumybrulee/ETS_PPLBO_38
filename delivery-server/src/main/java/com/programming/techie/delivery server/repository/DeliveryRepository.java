package com.programmingtechie.inventoryservice.repository;

import com.programmingtechie.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableKafkaStreams
public class DeliveryRepository {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String deliveryTopic = "delivery";
    private final String deliveryByBranchTopic = "delivery_by_branch";
    private final String deliveryByDateTopic = "delivery_by_date";

    @Autowired
    public DeliveryRepository(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void addDelivery(Delivery delivery) throws JsonProcessingException {
        String deliveryJson = objectMapper.writeValueAsString(delivery);
        kafkaTemplate.send(deliveryTopic, deliveryJson);
    }

    public List<Delivery> getDeliveriesByBranch(String branch) {
        // Implementasi ini tidak membutuhkan Kafka Streams
        // cukup menggunakan DeliveryService.getDeliveriesByBranch()
        return DeliveryService.getInstance().getDeliveriesByBranch(branch);
    }

    public List<Delivery> getDeliveriesByDate(String date) {
        // Implementasi ini tidak membutuhkan Kafka Streams
        // cukup menggunakan DeliveryService.getDeliveriesByDate()
        return DeliveryService.getInstance().getDeliveriesByDate(date);
    }

    // Kafka Streams implementation for delivery by branch
    @SuppressWarnings("unchecked")
    @KafkaListener(topics = deliveryTopic, groupId = "delivery-by-branch-group")
    public void processDeliveryByBranch(StreamsBuilder streamsBuilder) {
        KStream<String, String> source = streamsBuilder.stream(deliveryTopic);

        KTable<String, Long> deliveryByBranch = source
                .selectKey((key, value) -> {
                    try {
                        Delivery delivery = objectMapper.readValue(value, Delivery.class);
                        return delivery.getBranch();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .groupByKey()
                .count(Materialized.<String, Long, KeyValueStore<org.apache.kafka.common.utils.Bytes, byte[]>>as("delivery_by_branch_count")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));

        deliveryByBranch.toStream().map((key, value) -> new ProducerRecord<>(deliveryByBranchTopic, key, value.toString()))
                .to(deliveryByBranchTopic, Produced.with(Serdes.String(), Serdes.String()));
    }

    // Kafka Streams implementation for delivery by date
    @SuppressWarnings("unchecked")
    @KafkaListener(topics = deliveryTopic, groupId = "delivery-by-date-group")
    public void processDelivery(Delivery delivery) {
    // Validate delivery data
    if (delivery.getBranch() == null || delivery.getDate() == null || delivery.getCustomerName() == null) {
        logger.error("Invalid delivery data: " + delivery.toString());
        return;
    }

    // Save delivery to database
    deliveryRepository.save(delivery);

    // Send notification to customer
    Notification notification = new Notification(delivery.getCustomerName(), "Your delivery is on the way!");
    kafkaProducer.send("delivery-notification", notification);
}
}