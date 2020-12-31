package org.camunda.bpm.spring.boot.example.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("setallevents")
public class SetAllEvents implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("criando eventos..");
		execution.getProcessEngineServices().getRuntimeService().createSignalEvent("sinal").send();
		execution.setVariable("start", "1");
	}

}
