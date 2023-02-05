package com.uber.cadencedemo;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadencedemo.workflow.OrderWorkFlow;
import com.uber.cadencedemo.workflow.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/cadence")
public class TestController {

    @GetMapping("/start/{order-id}")
    public ResponseEntity<String> startWorkflow(@PathVariable(name = "order-id") String orderId) {
        Order order = Order.builder().orderId(orderId).
                accountAddress("borivali east").
                accountEmail("ambani@jio.com").build();
        WorkflowOptions workflowOptions = new WorkflowOptions.Builder()
                .setExecutionStartToCloseTimeout(Duration.ofMinutes(5))
                .setTaskList("order-tasks")
                .setWorkflowId(orderId)
                .build();
        WorkflowClient workflowClient = WorkflowClient.newInstance(
                new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                WorkflowClientOptions.newBuilder().setDomain("rishabh-domain").build());

        OrderWorkFlow ordersWorkflow = workflowClient.newWorkflowStub(OrderWorkFlow.class, workflowOptions);


        WorkflowClient.start(ordersWorkflow::processOrder, order);
        return ResponseEntity.ok("started");
    }

    @GetMapping("/stock/{order-id}")
    public ResponseEntity<String> makeInStock(@PathVariable(name = "order-id") String orderId) {
        WorkflowClient workflowClient = WorkflowClient.newInstance(
                new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                WorkflowClientOptions.newBuilder().setDomain("rishabh-domain").build());

        OrderWorkFlow orderWorkFlow = workflowClient.newWorkflowStub(OrderWorkFlow.class, orderId);
        orderWorkFlow.waitForStock();
        return ResponseEntity.ok("stocked");
    }
}
