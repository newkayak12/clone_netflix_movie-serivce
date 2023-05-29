package com.netflix_clone.movieservice.configure.rabbit;

import com.netflix_clone.movieservice.enums.Rabbit;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "rabbit_configuration")
public class Config {
    private final String queueName = Rabbit.Queue.MOVIE.getName();

    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to( new TopicExchange("netflix-clone-user") ).with("profile.*");
    }


}