package org.mykafka.producererrorhandlingsynchronous.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@GlobalChannelInterceptor
public class LoggingChannelInterceptor implements ChannelInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingChannelInterceptor.class);
    private static final String REQUEST_ID = "requestId";
    private static final String CORRELATION_ID = "correlationId";

    public LoggingChannelInterceptor() {
    }

    public Message<?> preSend(Message<?> msg, MessageChannel mc) {
        if (msg.getHeaders().get(REQUEST_ID) == null) {
            log.trace("adding requestId to message...");
            return this.addRequestIdHeader(msg);
        } else {
            this.putRequestIdToMdc(msg.getHeaders().get(REQUEST_ID, String.class));
            log.trace("using requestId from message...");
            return msg;
        }
    }

    public void postSend(Message<?> msg, MessageChannel mc, boolean sent) {
        log.info("Send message: sent={}, mc={}, msg={}, payload={}", sent, mc, msg, msg.getPayload());
    }

    private String getRequestIdFromMdc() {
        String requestId = MDC.get(REQUEST_ID);
        if (requestId == null) {
            requestId = MDC.get(CORRELATION_ID);
        }

        return requestId == null ? UUID.randomUUID().toString() : requestId;
    }

    private void putRequestIdToMdc(String requestId) {
        MDC.put(REQUEST_ID, requestId);
        MDC.put(CORRELATION_ID, requestId);
    }

    private <T> Message<T> addRequestIdHeader(Message<T> msg) {
        Map<String, Object> rawHeaderMap = new HashMap(msg.getHeaders());
        rawHeaderMap.put(REQUEST_ID, this.getRequestIdFromMdc());
        return new GenericMessage(msg.getPayload(), rawHeaderMap);
    }
}