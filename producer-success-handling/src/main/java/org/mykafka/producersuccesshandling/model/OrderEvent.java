package org.mykafka.producersuccesshandling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderEvent {

    private Integer orderEventId;
    private String orderNumber;
    private String customerNumber;

}