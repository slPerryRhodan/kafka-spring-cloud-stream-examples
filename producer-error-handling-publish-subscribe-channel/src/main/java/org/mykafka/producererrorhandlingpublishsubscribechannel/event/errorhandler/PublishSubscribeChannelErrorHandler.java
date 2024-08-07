package org.mykafka.producererrorhandlingpublishsubscribechannel.event.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
public class PublishSubscribeChannelErrorHandler {

    @Bean
    public PublishSubscribeChannel errorChannel() {
        return new MyErrorChannel(myErrorHandler());
    }

    private MessageHandler myErrorHandler() {
        return v -> {
            log.error("Error: {}", v);
        };
    }

    public static class MyErrorChannel extends PublishSubscribeChannel {
        public MyErrorChannel(MessageHandler handler) {
            this.dispatcher.addHandler(handler);
        }
    }
}