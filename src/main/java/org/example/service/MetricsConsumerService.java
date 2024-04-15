package org.example.service;

import org.example.model.Metric;
import org.example.repository.MetricRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

@Service
public class MetricsConsumerService {
    private static final Logger log = LoggerFactory.getLogger(MetricsConsumerService.class);
    private final MetricRepository metricRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MetricsConsumerService(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
        this.objectMapper = new ObjectMapper();
    }

    public List<Metric> findAllMetrics() {
        return metricRepository.findAll();
    }

    public Optional<Metric> findMetricById(Long id) {
        return metricRepository.findById(id);
    }

    public Metric saveMetric(Metric metric) {
        return metricRepository.save(metric);
    }

    @Transactional
    @KafkaListener(topics = "metrics-topic", groupId = "metrics-group")
    public void processMetric(String message) {
        log.info("Received Kafka message: {}", message);
        Metric metric = parseMetric(message);
        if (metric != null) {
            metricRepository.save(metric);
            log.info("Metric saved: {}", metric);
        } else {
            log.error("Received null metric object after parsing JSON message.");
        }
    }

    private Metric parseMetric(String json) {
        try {
            return objectMapper.readValue(json, Metric.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON message: " + e.getMessage(), e);
            return null;
        }
    }
}
