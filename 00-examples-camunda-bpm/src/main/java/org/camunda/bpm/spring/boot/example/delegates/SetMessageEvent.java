package org.camunda.bpm.spring.boot.example.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("setMessageEvent")
public class SetMessageEvent implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
//		Random random = new Random();
//		System.out.println("Gerando numero");
//		int randomWithNextInt = random.nextInt(10);
//		execution.setVariable("numero", randomWithNextInt);
//		System.out.println("Numero gerado: " + execution.getVariable("numero"));
//		if (randomWithNextInt % 2 == 0) {
//		Thread.sleep(5000);}
		System.out.println("message");
		execution.getProcessEngine().getRuntimeService().createMessageCorrelation("mymessage").correlate();

	}

}
