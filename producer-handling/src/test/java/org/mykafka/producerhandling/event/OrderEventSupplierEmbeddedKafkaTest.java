package org.mykafka.producerhandling.event;

import org.junit.jupiter.api.Test;
import org.mykafka.producerhandling.event.errorhandler.ServiceActivatorErrorHandler;
import org.mykafka.producerhandling.event.successhandler.ServiceActivatorSuccessHandler;
import org.mykafka.producerhandling.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(partitions = 1, ports = 0)
@ActiveProfiles(value = "test")
class OrderEventSupplierEmbeddedKafkaTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private OrderEventSupplier orderEventSupplier;

    @MockitoSpyBean
    private StreamBridge streamBridge;
    @MockitoSpyBean
    private ServiceActivatorSuccessHandler serviceActivatorSuccessHandler;
    @MockitoSpyBean
    private ServiceActivatorErrorHandler serviceActivatorErrorHandler;

    @Test
    void sendOrderUpdateEvent_kafkaIsRunning_successHandlerCalled() {
        // given
        var orderEvent = OrderEvent.builder()
                .orderEventId(1)
                .orderNumber("orderNumber")
                .customerNumber("customerNumber")
                .build();

        // when
        orderEventSupplier.sendOrderUpdateEvent(orderEvent);

        // then
        verify(streamBridge).send(any(), any());
        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(3, SECONDS)
                .untilAsserted(() -> {
                    verify(serviceActivatorSuccessHandler).successResultUpdateOrder(any());
                    verify(serviceActivatorErrorHandler, never()).orderUpdateEventErrorHandle(any());
                });
    }

    @Test
    void sendOrderUpdateEvent_kafkaIsNotRunning_errorHandlerCalled() {
        // given
        var orderEvent = OrderEvent.builder()
                .orderEventId(2)
                .orderNumber("orderNumber2")
                .customerNumber("customerNumber2")
                .build();
        embeddedKafka.destroy();

        // when
        orderEventSupplier.sendOrderUpdateEvent(orderEvent);

        // then
        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(3, SECONDS)
                .untilAsserted(() -> verify(serviceActivatorSuccessHandler, never()).successResultUpdateOrder(any()));
        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(3, SECONDS)
                .untilAsserted(() -> {
                    verify(serviceActivatorSuccessHandler, never()).successResultUpdateOrder(any());
                    verify(serviceActivatorErrorHandler).orderUpdateEventErrorHandle(any());
                });
    }
}