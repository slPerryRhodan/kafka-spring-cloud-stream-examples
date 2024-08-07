package org.mykafka.producererrorhandlingerrorhandlerdefinition.event.successhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.mykafka.producererrorhandlingerrorhandlerdefinition.model.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@Slf4j
@Configuration
public class ServiceActivatorSuccessHandler {

    @Bean
    MessageChannel orderUpdateMeta() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "orderUpdateMeta")
    public void sendResultOrderUpdate(GenericMessage<OrderEvent> result) {
        log.info("Successful send OrderUpdateEvent: orderEvent= [{}]", result.getHeaders().get(KafkaHeaders.RECORD_METADATA, RecordMetadata.class));
    }
}