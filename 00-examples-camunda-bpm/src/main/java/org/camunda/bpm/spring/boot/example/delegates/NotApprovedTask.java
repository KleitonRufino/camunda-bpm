package org.camunda.bpm.spring.boot.example.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("notapprovedtask")
public class NotApprovedTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Not Approved");
		execution.setVariable("approved", false);
	}

}
