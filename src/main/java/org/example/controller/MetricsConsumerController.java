package org.example.controller;

import org.example.service.MetricsConsumerService;
import org.example.model.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/metrics")
public class MetricsConsumerController {
    private static final Logger log = LoggerFactory.getLogger(MetricsConsumerController.class);

    @Autowired
    private MetricsConsumerService metricsService;

    @GetMapping
    public ResponseEntity<List<Metric>> getAllMetrics() {
        log.info("Received request to get all metrics");
        List<Metric> metrics = metricsService.findAllMetrics();
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metric> getMetricById(@PathVariable Long id) {
        log.info("Received request to get metric by ID: {}", id);
        Optional<Metric> metric = metricsService.findMetricById(id);
        return metric.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
