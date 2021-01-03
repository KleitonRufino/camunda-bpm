package org.camunda.bpm.spring.boot.example.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class TaskAssignmentListener implements TaskListener {

     private final static Logger LOGGER = Logger.getLogger(TaskAssignmentListener.class.getName());

    @Override
    public void notify(DelegateTask delegateTask) {

        String assignee = delegateTask.getAssignee();
        String taskId = delegateTask.getId();

        LOGGER.info("assigne: " + assignee);
        LOGGER.info("task: " + taskId);

    }
}
