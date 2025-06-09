package org.mykafka.producersuccesshandling.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mykafka.producersuccesshandling.model.OrderEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventSupplier {
    private static final String ORDER_UPDATE_BINDING_NAME = "updateOrder";
    private static final String ORDER_UPDATE_SECOND_BINDING_NAME = "updateSecondOrder";

    private final StreamBridge streamBridge;

    public void sendOrderUpdateEvent(final OrderEvent orderEvent) {
        try {
            var uuidKey = UUID.randomUUID().toString();
            Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.KEY, uuidKey.getBytes());
            var message = MessageBuilder.withPayload(orderEvent)
                                        .copyHeaders(headers)
                                        .build();
            var sent = streamBridge.send(ORDER_UPDATE_BINDING_NAME, message);
            if (sent) {
                log.info("Sending OrderUpdate for order was successful: orderNumber=[{}], kafkaHeaderKey=[{}]", orderEvent.getOrderNumber(), uuidKey);
            } else {
                log.error("Sending OrderUpdate for order failed: orderNumber=[{}], kafkaHeaderKey=[{}]", orderEvent.getOrderNumber(), uuidKey);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public void sendSecondOrderUpdateEvent(final OrderEvent orderEvent) {
        try {
            var uuidKey = UUID.randomUUID().toString();
            Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.KEY, uuidKey.getBytes());
            var message = MessageBuilder.withPayload(orderEvent)
                                        .copyHeaders(headers)
                                        .build();
            boolean sent = streamBridge.send(ORDER_UPDATE_SECOND_BINDING_NAME, message);
            if (sent) {
                log.info("Sending second OrderUpdate for order was successful: orderNumber=[{}], kafkaHeaderKey=[{}]", orderEvent.getOrderNumber(), uuidKey);
            } else {
                log.error("Sending second OrderUpdate for order failed: orderNumber=[{}], kafkaHeaderKey=[{}]", orderEvent.getOrderNumber(), uuidKey);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}