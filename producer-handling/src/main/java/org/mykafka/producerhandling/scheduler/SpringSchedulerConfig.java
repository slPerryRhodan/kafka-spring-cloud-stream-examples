package org.mykafka.producerhandling.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mykafka.producerhandling.model.OrderEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SpringSchedulerConfig {

    private final SchedulerSupplierService schedulerSupplierService;


//    @Scheduled(fixedDelay = 2000)
    public void scheduleFixedDelayTask() {
        log.info("Start fixed delay task - {}", (System.currentTimeMillis() / 1000));
        var orderEvent = OrderEvent.builder()
                                   .orderEventId(123)
                                   .orderNumber("orderNumber")
                                   .customerNumber("customerNumber")
                                   .build();
        schedulerSupplierService.sendOrderUpdateEvent(orderEvent);
        log.info("Finished Fixed delay task - {}", (System.currentTimeMillis() / 1000));
    }
}