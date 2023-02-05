package com.uber.cadencedemo.activities.impl;

import com.uber.cadencedemo.activities.OrderActivities;
import com.uber.cadencedemo.activities.model.StockInfo;

public class StockService implements OrderActivities {
    @Override
    public StockInfo checkStock(String orderId) {
        return null;
    }

    @Override
    public void notifyDelay(String emailAddress, Long eta) {

    }

    @Override
    public String packageAndSendOrder(String orderId, String address) {
        return null;
    }

    @Override
    public void notifySent(String emailAddress, String trackingId) {

    }
}
