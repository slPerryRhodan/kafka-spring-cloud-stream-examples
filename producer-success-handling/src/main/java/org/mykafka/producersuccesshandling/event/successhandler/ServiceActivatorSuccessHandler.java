package org.mykafka.producersuccesshandling.event.successhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.mykafka.producersuccesshandling.model.OrderEvent;
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
    MessageChannel updateOrderSuccessChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "updateOrderSuccessChannel")
    public void successResultUpdateOrder(GenericMessage<OrderEvent> result) {
        log.info("Successful send OrderUpdateEvent: orderNumber= [{}]", result.getPayload().getOrderNumber());
    }

    @Bean
    MessageChannel updateSecondSuccessChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "updateSecondSuccessChannel")
    public void successResultUpdateSecondOrder(GenericMessage<OrderEvent> result) {
        log.info("Successful send second OrderUpdateEvent: orderEvent= [{}]", result.getHeaders().get(KafkaHeaders.RECORD_METADATA, RecordMetadata.class));
    }
}