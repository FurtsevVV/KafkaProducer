package com.zakat.kafkaexample.somepapka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping(value = "/list")
    public void sendStringListToKafkaTopic(@RequestParam("message") String message)
    {
        List<String> list = new ArrayList<>();
        list.add(message);
        list.add(message + " add");
        list.add(message + " add 2");
        this.producerService.sendList(TOPIC_NAME, list);
    }



    @PostMapping(value = "/sendmsg")
    public void sendMessageObjectToKafkaTopic(@RequestBody Message obj)
    {
        this.producerService.sendObject(TOPIC_NAME, obj);
    }


}
