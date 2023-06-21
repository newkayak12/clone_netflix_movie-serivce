package com.netflix_clone.movieservice.component.configure.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component(value = "rabbit_consumer")
@RequiredArgsConstructor
@Slf4j
public class ProfileMessageConsumer {
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "netflix-clone-movie")
    public void profileSaveRabbit(final Message message) throws IOException {
        log.warn("MESSAGE {}", message);
        byte[] body = message.getBody();
        log.warn("????? {}", objectMapper.readValue(new String(body), Map.class));

    }

}