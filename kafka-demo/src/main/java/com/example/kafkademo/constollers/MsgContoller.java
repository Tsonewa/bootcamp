package com.example.kafkademo.constollers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/message")
public class MsgContoller {

    private KafkaTemplate<String, String> kafkaTemplate;

    public MsgContoller(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody MsgRequest request){
        kafkaTemplate.send("testTopic", request.msg());
    }
}
