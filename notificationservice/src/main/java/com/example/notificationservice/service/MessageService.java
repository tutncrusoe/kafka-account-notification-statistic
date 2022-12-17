package com.example.notificationservice.service;

import com.example.notificationservice.model.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private EmailService emailService;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MessageDTO messageDTO) {
        logger.info("Received new message: " + messageDTO.getSubject(), messageDTO.getTo());
        emailService.sendEmail(messageDTO);
    }
}
