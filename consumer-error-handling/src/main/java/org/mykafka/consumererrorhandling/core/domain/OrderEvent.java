package org.mykafka.consumererrorhandling.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public class OrderEvent {

    private Integer orderEventId;

    private String orderNumber;

    private String customerNumber;

    public Integer getOrderEventId() {
        return orderEventId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }
}