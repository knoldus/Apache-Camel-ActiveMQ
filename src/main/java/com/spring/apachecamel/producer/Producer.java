package com.spring.apachecamel.producer;

import com.spring.apachecamel.dto.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jms.Queue;

@RestController
@RequestMapping("/produce")
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @PostMapping("/message")
    public Product sendMessage(@RequestBody Product product) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String productAsJson = mapper.writeValueAsString(product);

            jmsTemplate.convertAndSend(queue, productAsJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}
