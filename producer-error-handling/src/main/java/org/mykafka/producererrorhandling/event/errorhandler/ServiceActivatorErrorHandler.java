package org.mykafka.producererrorhandling.event.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ServiceActivatorErrorHandler {

//    @Bean
//    MessageChannel errorChannel() {
//        return new DirectChannel();
//    }
//
//    @ServiceActivator(inputChannel = "errorChannel")
//    public void orderUpdateEventErrorHandle(GenericMessage<KafkaSendFailureException> msg) {
//        log.error("ServiceActivator error: {}", msg.getPayload().getRecord());
//    }
}