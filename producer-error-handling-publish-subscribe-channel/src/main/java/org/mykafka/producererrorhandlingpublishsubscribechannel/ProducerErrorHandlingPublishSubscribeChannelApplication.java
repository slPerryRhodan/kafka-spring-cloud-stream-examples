package org.mykafka.producererrorhandlingpublishsubscribechannel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan
public class ProducerErrorHandlingPublishSubscribeChannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerErrorHandlingPublishSubscribeChannelApplication.class, args);
    }
}
