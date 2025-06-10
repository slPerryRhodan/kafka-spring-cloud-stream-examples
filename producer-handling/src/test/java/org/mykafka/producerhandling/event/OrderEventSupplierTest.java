package org.mykafka.producerhandling.event;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mykafka.producerhandling.event.errorhandler.ServiceActivatorErrorHandler;
import org.mykafka.producerhandling.event.successhandler.ServiceActivatorSuccessHandler;
import org.mykafka.producerhandling.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@ActiveProfiles(value = "test")
class OrderEventSupplierTest {

    @Autowired
    private OrderEventSupplier orderEventSupplier;

    @MockitoSpyBean
    private StreamBridge streamBridge;
    @MockitoSpyBean
    private ServiceActivatorSuccessHandler serviceActivatorSuccessHandler;
    @MockitoSpyBean
    private ServiceActivatorErrorHandler serviceActivatorErrorHandler;

    private static ConfluentKafkaContainer kafka;

    @BeforeAll
    static void setup() {
        var imageVersion = PropertiesExtractor.getProperty("kafka-testcontainers.image-version");
        kafka = new ConfluentKafkaContainer(DockerImageName.parse(imageVersion));
        kafka.start();
        System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
    }

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
        kafka.stop();

        // when
        orderEventSupplier.sendOrderUpdateEvent(orderEvent);

        // then
        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(3, SECONDS)
                .untilAsserted(() -> {
                    verify(serviceActivatorSuccessHandler, never()).successResultUpdateOrder(any());
                    verify(serviceActivatorErrorHandler).orderUpdateEventErrorHandle(any());
                });
    }

    private static class PropertiesExtractor {
        private static final Properties properties;
        static {
            properties = new Properties();
            var url = PropertiesExtractor.class.getClassLoader().getResource("testcontainers.properties");
            try{
                properties.load(new FileInputStream(url.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static String getProperty(String key){
            return properties.getProperty(key);
        }
    }
}