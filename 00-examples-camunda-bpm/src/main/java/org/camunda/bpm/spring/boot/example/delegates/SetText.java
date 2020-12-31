package org.camunda.bpm.spring.boot.example.delegates;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("settext")
public class SetText implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Random random = new Random();
		System.out.println("Gerando numero");
		int randomWithNextInt = random.nextInt(10);
		System.out.println("Número gerado: " + randomWithNextInt);
		if (randomWithNextInt % 2 == 0) {
			execution.setVariable("someText", "Camunda");
		} else {
			execution.setVariable("someText", "Camunda2");
			execution.setVariable("par", false);
		}
		execution.setVariable("approved", "0");
		System.out.println("Texto: " + execution.getVariable("someText"));
	}

}
