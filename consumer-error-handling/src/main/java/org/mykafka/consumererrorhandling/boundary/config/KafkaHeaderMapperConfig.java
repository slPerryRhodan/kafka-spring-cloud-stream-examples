package org.mykafka.consumererrorhandling.boundary.config;

import org.apache.kafka.common.header.Header;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaderMapper;
import org.springframework.kafka.support.SimpleKafkaHeaderMapper;

@Configuration
public class KafkaHeaderMapperConfig {
    private static final String REQUEST_ID = "requestId";
    private static final String CORRELATION_ID = "correlationId";

    public KafkaHeaderMapperConfig() {
    }

    @Bean
    public KafkaHeaderMapper headerMapper() {
        var simpleKafkaHeaderMapper = new SimpleKafkaHeaderMapper() {
            protected Object headerValueToAddIn(Header header) {
                var value = !REQUEST_ID.equals(header.key()) && !CORRELATION_ID.equals(header.key())
                               ? super.headerValueToAddIn(header)
                               : new String(header.value());

                return value;
            }
        };
        simpleKafkaHeaderMapper.setMapAllStringsOut(true);
        return simpleKafkaHeaderMapper;
    }
}