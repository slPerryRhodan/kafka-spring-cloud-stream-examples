package org.mykafka.producersuccesshandling.event;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mykafka.producersuccesshandling.event.errorhandler.ServiceActivatorErrorHandler;
import org.mykafka.producersuccesshandling.event.successhandler.ServiceActivatorSuccessHandler;
import org.mykafka.producersuccesshandling.model.OrderEvent;
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
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@ExtendWith(MockitoExtension.class)
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
    public static void setup() {
        var imageVersion = PropertiesExtractor.getProperty("kafka-testcontainers.image-version");
        kafka = new ConfluentKafkaContainer(DockerImageName.parse(imageVersion));
        kafka.start();
        System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
    }

    @Test
    void sendOrderUpdateEvent_kafkaIsRunning_successHandlerCalled() {
        orderEventSupplier.sendOrderUpdateEvent(OrderEvent.builder()
                .orderEventId(1)
                .orderNumber("orderNumber")
                .customerNumber("customerNumber")
                .build());

        verify(streamBridge).send(any(), any());
        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(3, SECONDS)
                .untilAsserted(() -> verify(serviceActivatorSuccessHandler).successResultUpdateOrder(any()));
    }

    @Test
    void sendOrderUpdateEvent_kafkaIsNotRunning_errorHandlerCalled() {
        orderEventSupplier.sendOrderUpdateEvent(OrderEvent.builder()
                .orderEventId(1)
                .orderNumber("orderNumber")
                .customerNumber("customerNumber")
                .build());

        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(3, SECONDS)
                .untilAsserted(() -> verify(serviceActivatorSuccessHandler).successResultUpdateOrder(any()));

        kafka.stop();

        orderEventSupplier.sendOrderUpdateEvent(OrderEvent.builder()
                .orderEventId(2)
                .orderNumber("orderNumber2")
                .customerNumber("customerNumber2")
                .build());
        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(3, SECONDS)
                .untilAsserted(() -> verify(serviceActivatorErrorHandler).orderUpdateEventErrorHandle(any()));
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