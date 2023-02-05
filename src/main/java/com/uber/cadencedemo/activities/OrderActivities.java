package com.uber.cadencedemo.activities;

import com.uber.cadence.activity.ActivityMethod;
import com.uber.cadencedemo.activities.model.StockInfo;

public interface OrderActivities {

    @ActivityMethod
    StockInfo checkStock(String orderId);

    @ActivityMethod
    void notifyDelay(String emailAddress, Long eta);

    @ActivityMethod
    String packageAndSendOrder(String orderId, String address);

    @ActivityMethod
    void notifySent(String emailAddress, String trackingId);
}
