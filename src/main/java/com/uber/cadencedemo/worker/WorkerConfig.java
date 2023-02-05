package com.uber.cadencedemo.worker;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadencedemo.activities.impl.NotificationService;
import com.uber.cadencedemo.workflow.impl.OrderWorkFlowImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WorkerConfig {

    @PostConstruct
    public void startWorker() {
        WorkflowClient workflowClient = WorkflowClient.newInstance(
                new WorkflowServiceTChannel(
                        ClientOptions.defaultInstance()),
                WorkflowClientOptions.newBuilder().setDomain("rishabh-domain").build());

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker("order-tasks");

        worker.registerWorkflowImplementationTypes(OrderWorkFlowImpl.class);
        worker.registerActivitiesImplementations( new NotificationService());

        factory.start();
    }

}
