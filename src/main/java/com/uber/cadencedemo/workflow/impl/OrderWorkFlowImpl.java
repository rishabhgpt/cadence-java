package com.uber.cadencedemo.workflow.impl;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.workflow.Workflow;
import com.uber.cadencedemo.activities.OrderActivities;
import com.uber.cadencedemo.activities.model.StockInfo;
import com.uber.cadencedemo.workflow.OrderWorkFlow;
import com.uber.cadencedemo.workflow.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Component
public class OrderWorkFlowImpl implements OrderWorkFlow {

    StockInfo stockInfo;

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public void processOrder(Order order) {
        // 1: Configure the workflow stub
        final OrderActivities activities = Workflow.newActivityStub(OrderActivities.class,
                new ActivityOptions.Builder()
                        .setRetryOptions(new RetryOptions.Builder()
                                .setInitialInterval(Duration.ofSeconds(10))
                                .setMaximumAttempts(3)
                                .build())
                        .setScheduleToCloseTimeout(Duration.ofMinutes(5)).build());

        // Check stock
        stockInfo = activities.checkStock(order.getOrderId());

        //make api call
        restTemplate.getForEntity("http://127.0.0.1:8080", Object.class);
        if (!stockInfo.isInStock()) {
            // Notify customer of delay
            activities.notifyDelay(
                    order.getAccountEmail(),
                    stockInfo.getRestockEta());

            // 2: Wait for restock
            Workflow.await(()-> stockInfo.isInStock());
        }

        // Package and send, returning tracking reference
        String trackingId = activities.packageAndSendOrder(
                order.getOrderId(),
                order.getAccountAddress());

        // Notify customer
        activities.notifySent(order.getAccountEmail(), trackingId);
    }

    @Override
    public void waitForStock() {
        stockInfo.setInStock(true);
    }
}
