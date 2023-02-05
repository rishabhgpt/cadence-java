package com.uber.cadencedemo.activities.impl;

import com.uber.cadencedemo.activities.OrderActivities;
import com.uber.cadencedemo.activities.model.StockInfo;

public class NotificationService implements OrderActivities {
    @Override
    public StockInfo checkStock(String orderId) {
        return StockInfo.builder().orderId(orderId).inStock(false).restockEta(2000L).restockWait(3000L).build();
    }

    @Override
    public void notifyDelay(String emailAddress, Long eta) {
        System.out.println("sending email to "+emailAddress+" to wait "+eta);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String packageAndSendOrder(String orderId, String address) {
        System.out.println("sending order "+orderId+" to address "+address);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sent";
    }

    @Override
    public void notifySent(String emailAddress, String trackingId) {
        System.out.println("sending email to "+emailAddress+" with tracking id "+trackingId);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
