package com.example.accountservice.controller;

import com.example.accountservice.model.AccountDTO;
import com.example.accountservice.model.MessageDTO;
import com.example.accountservice.model.StatisticDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate; // Object: JSON serialization

    @PostMapping("/new")
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        StatisticDTO statisticDTO = new StatisticDTO("Account " + accountDTO.getEmail() + " is created", new Date());

        // send message
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(accountDTO.getEmail());
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Create new account");
        messageDTO.setContent("Created successfully!");

        kafkaTemplate.send("notification", messageDTO);
        kafkaTemplate.send("statistic", statisticDTO);

        return accountDTO;
    }
}
