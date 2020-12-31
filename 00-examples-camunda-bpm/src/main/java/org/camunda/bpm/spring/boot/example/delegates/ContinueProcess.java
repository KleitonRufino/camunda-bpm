package org.camunda.bpm.spring.boot.example.delegates;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("continueprocess")
public class ContinueProcess implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
	
		Random random = new Random();
		System.out.println("Gerando numero");
		int randomWithNextInt = random.nextInt(10);
		execution.setVariable("numero", randomWithNextInt);
		System.out.println("Numero gerado: " + randomWithNextInt);
		if(randomWithNextInt%2==0) {
			System.out.println("Criando Msg continueprocess");
			execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("continueprocess")
			.correlate();
		}
	}

}
