package com.zakat.kafkaexample.somepapka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

private final ProducerService producerService;
    private static final String TOPIC_NAME = "topic1";
    private static final String GROUP_ID = "group_id";

    @PostMapping(value = "/send")
    public void sendMessageStringToKafkaTopic(@RequestParam("message") String message)
    {
        this.producerService.send(TOPIC_NAME, message);
    }

    @PostMapping(value = "/sendmsg")
    public void sendMessageObjectToKafkaTopic(@RequestBody Message obj)
    {
        this.producerService.sendObject(TOPIC_NAME, obj);
    }


}
