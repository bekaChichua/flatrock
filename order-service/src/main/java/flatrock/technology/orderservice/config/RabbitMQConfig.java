package flatrock.technology.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    Queue queue1() {
        return new Queue("Queue1", false);
    }

    @Bean
    DirectExchange direct() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    Binding fanoutBinding(Queue queue1, DirectExchange direct) {
        return BindingBuilder.bind(queue1).to(direct).with("routing");
    }

    @Bean
    MessageConverter fanoutMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean("rabbitFanout")
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(fanoutMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Declarables fanoutBindings() {
        Queue fanoutQueue1 = new Queue("fanout.notification", false);
        Queue fanoutQueue2 = new Queue("fanout.packaging", false);
        FanoutExchange fanoutExchange = new FanoutExchange("fanout.exchange");

        return new Declarables(
                fanoutQueue1,
                fanoutQueue2,
                fanoutExchange,
                BindingBuilder.bind(fanoutQueue1).to(fanoutExchange),
                BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
    }
}
