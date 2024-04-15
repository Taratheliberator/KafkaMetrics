package org.example.service;

import org.example.model.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MetricsProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MetricsProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMetricToKafka(Metric metric) {
        try {
            String jsonMetric = objectMapper.writeValueAsString(metric);
            kafkaTemplate.send("metrics-topic", jsonMetric);
            return true;
        } catch (JsonProcessingException e) {

            return false;
        }
    }
}
