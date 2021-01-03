package org.camunda.bpm.spring.boot.example.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("msgservice")
public class MSGService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MSG Service 1");
		execution.setVariable("servicevar", "servicevalue");
	}
//
//	@Override
//	public void execute(ActivityExecution execution) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println("MSG Service 1");
//		execution.setVariable("servicevar", "servicevalue");
//
//	}

}
