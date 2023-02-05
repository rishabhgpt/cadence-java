package com.uber.cadencedemo.workflow;

import com.uber.cadence.workflow.SignalMethod;
import com.uber.cadence.workflow.WorkflowMethod;
import com.uber.cadencedemo.workflow.model.Order;

public interface OrderWorkFlow {

    @WorkflowMethod
    void processOrder(Order order);

    @SignalMethod
    void waitForStock();
}
