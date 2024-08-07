package org.mykafka.consumererrorhandling.boundary.in.event;

import lombok.extern.slf4j.Slf4j;
import org.mykafka.consumererrorhandling.core.domain.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class UpdateOrderConsumer {

    @Bean
    public Consumer<Message<String>> orderConsumer() {
        return message -> {
            throw new RuntimeException("Update Order Failed");
        };
    }

    @Bean
    public Consumer<ErrorMessage> orderErrorHandler() {
        return message -> {
            log.error("Update Order Failed: message={}", message.getPayload());
        };
    }
}