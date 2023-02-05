package com.uber.cadencedemo.workflow.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private String orderId;
    private String accountEmail;
    private String accountAddress;
}
