package org.camunda.bpm.spring.boot.example.delegates;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("printpar")
public class PrintPar implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		int numero = (int) execution.getVariable("numero");
		System.out.println("Par: " + numero);
		if(numero <= 3)
			throw new BpmnError("error");
	}

}
