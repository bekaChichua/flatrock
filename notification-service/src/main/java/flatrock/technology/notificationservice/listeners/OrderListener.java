package flatrock.technology.notificationservice.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {
    @RabbitListener(queues = "Queue1")
    private void receive(Message message) {
        log.info("Message received: {}", message);
    }
}
