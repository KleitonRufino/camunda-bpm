package org.camunda.bpm.spring.boot.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessInstanceWithVariablesImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private RuntimeService service;

	@GetMapping(produces = "application/json")
	public String index() {
		ProcessInstance instance = service.startProcessInstanceByKey("decisionTableGatewayOr");
		ProcessInstanceWithVariablesImpl res = (ProcessInstanceWithVariablesImpl) instance;
		return "{\"approved\": " + res.getVariables().get("approved") + "}";
	}
	
	//http://localhost:8080/hello/startwithmsg/msg-s-1?springname=Jose&gender=male
	@GetMapping
	@RequestMapping("/startwithmsg/{msg}")
	public void msg(@PathVariable("msg") String msg, @RequestParam("springname") String springname, @RequestParam("gender") String gender) {
	     service.createMessageCorrelation(msg)
         .setVariable("springname", springname)
         .setVariable("gender", gender)
         .correlate();
	}
}
