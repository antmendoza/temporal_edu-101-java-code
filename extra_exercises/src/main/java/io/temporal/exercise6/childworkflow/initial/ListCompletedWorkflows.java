package io.temporal.exercise6.childworkflow.initial;

import io.temporal.api.workflowservice.v1.ListWorkflowExecutionsRequest;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

public class ListCompletedWorkflows {

  public static void main(String[] args) {

    final WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(io.temporal.serviceclient.WorkflowServiceStubsOptions.newBuilder()
        .setTarget("127.0.0.1:7233") // Default values, can be omitted
        .build());

    while (true) {

      // Paste this query in the UI, advance search
      final String query =
          "ExecutionStatus=\"Completed\" and (WorkflowType=\"MoneyTransferChildWorkflow\" "
              + "or WorkflowType=\"MoneyTransferWorkflow\")";

      int numClosedExecution =
          service
              .blockingStub()
              .listWorkflowExecutions(
                  // https://docs.temporal.io/visibility#search-attribute
                  ListWorkflowExecutionsRequest.newBuilder()
                      .setQuery(query)
                      .setNamespace("default")
                      .build())
              .getExecutionsCount();

      System.out.println(">>>>> numClosedExecution " + numClosedExecution);

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
