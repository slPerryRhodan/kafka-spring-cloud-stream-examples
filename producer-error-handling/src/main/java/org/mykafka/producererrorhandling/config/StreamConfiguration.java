package org.mykafka.producererrorhandling.config;

import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.integration.config.GlobalChannelInterceptor;

@Configuration
public class StreamConfiguration {
  @Bean
  @GlobalChannelInterceptor(patterns = "*orderUpdate.errors", order = Ordered.LOWEST_PRECEDENCE)
  public InputChannelInterceptor inputChannelInterceptor() {
    return new InputChannelInterceptor();
  }

  @Bean
  @GlobalChannelInterceptor(patterns = "*orderUpdate.errors", order = Ordered.HIGHEST_PRECEDENCE)
  public OutputChannelInterceptor outputChannelInterceptor(BindingServiceProperties properties) {
    return new OutputChannelInterceptor(properties);
  }
}