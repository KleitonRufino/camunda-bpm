package org.camunda.bpm.spring.boot.example.delegates;

import java.util.Random;

import org.camunda.bpm.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;

public class AsynchronousServiceTask extends AbstractBpmnActivityBehavior {

	public static final String EXECUTION_ID = "executionId";

	public void execute(final ActivityExecution execution) throws InterruptedException {
		System.out.println("thread");
		Random random = new Random();
		System.out.println("Gerando numero");
		int randomWithNextInt = random.nextInt(10);
		execution.setVariable("numero", randomWithNextInt);
		System.out.println("Numero gerado: " + randomWithNextInt);
		if (randomWithNextInt % 2 == 0) {
			Thread.sleep(5000);
		}
	}

}
