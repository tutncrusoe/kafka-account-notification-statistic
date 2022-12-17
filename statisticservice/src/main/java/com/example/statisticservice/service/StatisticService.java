package com.example.statisticservice.service;

import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(StatisticService.class);

    @Autowired
    private StatisticRepository statisticRepository;

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        logger.info("Received: " + statistic.getMessage());
        statisticRepository.save(statistic);
    }
}
