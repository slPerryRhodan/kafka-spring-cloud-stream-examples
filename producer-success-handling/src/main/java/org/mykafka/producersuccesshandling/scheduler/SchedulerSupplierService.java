package org.mykafka.producersuccesshandling.scheduler;

import lombok.RequiredArgsConstructor;
import org.mykafka.producersuccesshandling.event.OrderEventSupplier;
import org.mykafka.producersuccesshandling.model.OrderEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerSupplierService {

    private final OrderEventSupplier orderEventSupplier;

    public void sendOrderUpdateEvent(OrderEvent orderEvent) {
        orderEventSupplier.sendOrderUpdateEvent(orderEvent);
    }
}
