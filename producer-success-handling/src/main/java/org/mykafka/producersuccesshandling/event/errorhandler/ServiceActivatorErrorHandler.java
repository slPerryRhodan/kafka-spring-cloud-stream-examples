package org.mykafka.producersuccesshandling.event.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.support.KafkaSendFailureException;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@Slf4j
@Configuration
public class ServiceActivatorErrorHandler {

    @Bean
    MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "errorChannel")
    public void orderUpdateEventErrorHandle(GenericMessage<KafkaSendFailureException> message) {
            log.error("Error channel: failedKafkaMsg=[{}]", message.getPayload().getFailedMessage());
    }
}