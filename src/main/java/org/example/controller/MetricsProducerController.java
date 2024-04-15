package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.model.Metric;
import org.springframework.kafka.core.KafkaTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


@RestController
@RequestMapping("/metrics")
public class MetricsProducerController {
    private static final Logger log = LoggerFactory.getLogger(MetricsProducerController.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MetricsProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<?> publishMetric(@RequestBody Metric metric) {
        try {
            String jsonMetric = objectMapper.writeValueAsString(metric);
            // Логируем JSON перед отправкой
            log.info("Sending metric to Kafka: {}", jsonMetric);
            kafkaTemplate.send("metrics-topic", jsonMetric);
            return ResponseEntity.ok("Metric sent to Kafka");
        } catch (JsonProcessingException e) {
            log.error("Error converting metric to JSON", e);
            return ResponseEntity.badRequest().body("Error converting metric to JSON");
        }
    }

}
