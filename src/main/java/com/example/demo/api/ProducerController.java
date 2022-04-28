package com.example.demo.api;

import com.example.demo.config.RabbitMqConfig;
import com.example.demo.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author jrojas
 */
@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate template;

//    @PostMapping("/v1/api/consumer")
    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer")
    public String sendMessage(@RequestBody MessageDto messageDto) {
        messageDto.setMessageId(UUID.randomUUID().toString());
        messageDto.setMessageDate(new Date());
        template.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTING_KEY, messageDto);
        return "Mensaje direct enviado";
    }
    //Fanout metodo
    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer/fanout")
    public String sendMessage1(@RequestBody MessageDto messageDto) {
        messageDto.setMessageId(UUID.randomUUID().toString());
        messageDto.setMessageDate(new Date());
        template.convertAndSend(RabbitMqConfig.EXCHANGEFANOUTE," ", messageDto);
        return "Mensaje fanout enviado";
    }
    //TOPIC metodo
    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer/topic")
    public String sendMessage2(@RequestBody MessageDto messageDto) {
        messageDto.setMessageId(UUID.randomUUID().toString());
        messageDto.setMessageDate(new Date());
        System.out.print(messageDto.getMessage());
        //Depenciendo a que cola se pretenda enviar el mensaje, se debe cambiar el routingKey.
        template.convertAndSend(RabbitMqConfig.EXCHANGETOPIC,RabbitMqConfig.ROUTING_KEYTOPIC4+"hola", messageDto);
        return "Mensaje topic enviado";
    }
}
