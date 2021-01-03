package org.camunda.bpm.spring.boot.example.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MSGService2 implements ExecutionListener {
	Logger LOGGER = LoggerFactory.getLogger(MSGService2.class);
	Expression inj1;
	Expression inj2;
	String inj1val;
	String wish;

	// Expression text1
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {

		inj1val = (String) inj1.getValue(delegateExecution);
		wish = (String) inj2.getValue(delegateExecution);
		System.out.println("executed execution listeneer1 inj1 with value " + inj1val);
		System.out.println("executed execution listeneer1 inj2 with value " + wish);
	}
}
