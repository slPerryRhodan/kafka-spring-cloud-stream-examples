package org.mykafka.producererrorhandlingsynchronous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan
public class ProducerErrorHandlingSynchronousApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerErrorHandlingSynchronousApplication.class, args);
    }
}
