package com.netflix_clone.movieservice.component.configure.rabbit;

import com.netflix_clone.movieservice.component.configure.ConfigMsg;
import com.netflix_clone.movieservice.component.enums.Rabbit;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "rabbit_configuration")
public class Config {
    private final String queueName = Rabbit.Queue.MOVIE.getName();


    public Config() {
        ConfigMsg.msg("RabbitMQ");
    }
    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to( new TopicExchange("netflix-clone-user") ).with("profile.*");
    }


}