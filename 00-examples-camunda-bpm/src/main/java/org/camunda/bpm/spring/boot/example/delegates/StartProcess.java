package org.camunda.bpm.spring.boot.example.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("startprocess")
public class StartProcess implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Criando Msg startprocess");
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("startprocess").correlate();
	}

}
