package com.example.kafkademo.listners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListner {

    @KafkaListener(topics = "testTopic", groupId = "groupId")
    void listener(String data){
        System.out.println("The data has been received " + data + "<3");
    }
}
