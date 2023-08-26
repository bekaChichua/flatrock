package flatrock.technology.orderservice.controller;

import flatrock.technology.orderservice.config.MyConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Test {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @GetMapping("/")
    public String sendMessage() {
        rabbitTemplate.convertAndSend(exchange.getName(), "routing", "test");
        return "message sent";
    }
}
