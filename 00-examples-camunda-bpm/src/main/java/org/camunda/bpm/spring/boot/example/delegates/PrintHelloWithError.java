package org.camunda.bpm.spring.boot.example.delegates;

import java.util.Random;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("printhellowiitherror")
public class PrintHelloWithError implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Hello...");
		Random random = new Random();
		System.out.println("Gerando numero");
		int numero = random.nextInt(10);
		System.out.println("Número gerado: " + numero);
		if (numero <= 3) {
			throw new BpmnError("error");
		}

	}

}
