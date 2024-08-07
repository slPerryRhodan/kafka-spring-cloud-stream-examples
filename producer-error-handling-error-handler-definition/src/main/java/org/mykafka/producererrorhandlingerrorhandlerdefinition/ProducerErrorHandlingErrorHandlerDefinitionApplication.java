package org.mykafka.producererrorhandlingerrorhandlerdefinition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan
public class ProducerErrorHandlingErrorHandlerDefinitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerErrorHandlingErrorHandlerDefinitionApplication.class, args);
    }
}
