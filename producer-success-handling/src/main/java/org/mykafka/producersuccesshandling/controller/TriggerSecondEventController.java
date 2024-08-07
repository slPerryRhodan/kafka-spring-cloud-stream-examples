package org.mykafka.producersuccesshandling.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mykafka.producersuccesshandling.event.OrderEventSupplier;
import org.mykafka.producersuccesshandling.model.OrderEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TriggerSecondEventController {

    private final OrderEventSupplier orderEventSupplier;

    @PostMapping("/v2/orderevent")
    public ResponseEntity<OrderEvent> postSecondOrderEvent(@RequestBody @Valid OrderEvent orderEvent) {

        //invoke kafka producer
        orderEventSupplier.sendSecondOrderUpdateEvent(orderEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderEvent);
    }

}
