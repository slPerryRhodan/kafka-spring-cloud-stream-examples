package org.mykafka.producerhandling.scheduler;

import lombok.RequiredArgsConstructor;
import org.mykafka.producerhandling.event.OrderEventSupplier;
import org.mykafka.producerhandling.model.OrderEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerSupplierService {

    private final OrderEventSupplier orderEventSupplier;

    public void sendOrderUpdateEvent(OrderEvent orderEvent) {
        orderEventSupplier.sendOrderUpdateEvent(orderEvent);
    }
}
