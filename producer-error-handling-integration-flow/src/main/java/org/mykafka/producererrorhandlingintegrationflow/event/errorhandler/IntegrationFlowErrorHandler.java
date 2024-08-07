package org.mykafka.producererrorhandlingintegrationflow.event.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;

@Slf4j
@Configuration
public class IntegrationFlowErrorHandler {

    @Bean
    public IntegrationFlow integrationFlow() {
        return f -> f.channel("errorChannel")
                .handle((payload, messageHeaders) -> {
                    log.info("Received error payload: {}", payload);
                    return payload;
                });
    }
}