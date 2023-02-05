package com.uber.cadencedemo.activities.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockInfo {

    private String orderId;
    private boolean inStock;
    private long restockEta;
    private long restockWait;
}
