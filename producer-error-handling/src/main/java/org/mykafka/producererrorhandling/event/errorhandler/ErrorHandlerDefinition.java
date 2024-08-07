package org.mykafka.producererrorhandling.event.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.BinderCustomizer;
import org.springframework.cloud.stream.binder.kafka.KafkaMessageChannelBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ErrorHandlerDefinition {

//    @Bean
//    public Consumer<ErrorMessage> myUpdateOrderErrorHandler() {
//        return errorMessage -> {
//            log.error("myUpdateOrderErrorHandler error: {}", errorMessage);
//        };
//    }
//
//    @Bean
//    public BinderCustomizer binderCustomizer() {
//        return (binder, binderName) -> {
//            if (binder instanceof KafkaMessageChannelBinder kafkaMessageChannelBinder) {
//                var binding = kafkaMessageChannelBinder.getBindings();
//                log.info("Binding for channel: {}", binding);
//            }
//        };
//    }

}