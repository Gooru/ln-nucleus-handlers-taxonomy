package org.gooru.nucleus.handlers.taxonomy.processors.responses;

public class ExecutionResult<T> {

  private final T result;
  private final ExecutionStatus status;

  public ExecutionResult(T result, ExecutionStatus status) {
    this.result = result;
    this.status = status;
  }

  public T result() {
    return this.result;
  }

  public boolean isCompleted() {
    return (this.status == ExecutionStatus.FAILED || this.status == ExecutionStatus.SUCCESSFUL);
  }

  public boolean isSuccessful() {
    return (this.status == ExecutionStatus.SUCCESSFUL);
  }

  public boolean hasFailed() {
    return (this.status == ExecutionStatus.FAILED);
  }

  public boolean continueProcessing() {
    return (this.status == ExecutionStatus.CONTINUE_PROCESSING);
  }

  public enum ExecutionStatus {
    SUCCESSFUL,
    FAILED,
    CONTINUE_PROCESSING
  }
}
