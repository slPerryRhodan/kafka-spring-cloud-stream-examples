package org.mykafka.producererrorhandlingpublishsubscribechannel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mykafka.producererrorhandlingpublishsubscribechannel.event.OrderEventSupplier;
import org.mykafka.producererrorhandlingpublishsubscribechannel.model.OrderEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TriggerEventController {

    private final OrderEventSupplier orderEventSupplier;

    @PostMapping("/v1/orderevent")
    public ResponseEntity<OrderEvent> postOrderEvent(@RequestBody @Valid OrderEvent orderEvent) {

        //invoke kafka producer
        orderEventSupplier.sendOrderUpdateEvent(orderEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderEvent);
    }

}
