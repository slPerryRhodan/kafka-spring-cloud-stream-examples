package org.mykafka.producererrorhandlingerrorhandlerdefinition.event.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@Slf4j
@Configuration
public class ServiceActivatorErrorHandler {

    @ServiceActivator(inputChannel = "errorChannel")
    public void orderUpdateEventErrorHandle(GenericMessage<?> msg) {
        log.error("ServiceActivator error: {}", msg);
    }
}