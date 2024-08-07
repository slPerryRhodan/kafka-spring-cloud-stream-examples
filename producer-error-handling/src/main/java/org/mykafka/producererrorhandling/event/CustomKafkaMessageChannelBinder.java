package org.mykafka.producererrorhandling.event;

import org.springframework.cloud.stream.binder.kafka.KafkaMessageChannelBinder;
import org.springframework.cloud.stream.binder.kafka.properties.KafkaBinderConfigurationProperties;
import org.springframework.cloud.stream.binder.kafka.provisioning.KafkaTopicProvisioner;

public class CustomKafkaMessageChannelBinder extends KafkaMessageChannelBinder {

    @Override
    public String getBinderIdentity() {
        return super.getBinderIdentity();
    }

    public CustomKafkaMessageChannelBinder(KafkaBinderConfigurationProperties configurationProperties, KafkaTopicProvisioner provisioningProvider) {
        super(configurationProperties, provisioningProvider);
    }
}
