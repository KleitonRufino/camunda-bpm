package com.teste;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Rule;
import org.junit.Test;



public class SomeTests {

	@Rule
	public ProcessEngineRule processEngineRule = TestCoverageProcessEngineRuleBuilder.create().build();
	String json = "{\r\n" + "			  \"firstName\": \"John\",\r\n" + "			  \"lastName\" : \"doe\",\r\n"
			+ "			  \"age\"      : 26,\r\n" + "			  \"address\"  : {\r\n"
			+ "			    \"streetAddress\": \"naist street\",\r\n"
			+ "			    \"city\"         : \"Nara\",\r\n" + "			    \"postalCode\"   : \"630-0192\"\r\n"
			+ "			  },\r\n" + "			  \"phoneNumbers\": [\r\n" + "			    {\r\n"
			+ "			      \"type\"  : \"iPhone\",\r\n" + "			      \"number\": \"0123-4567-8888\"\r\n"
			+ "			    },\r\n" + "			    {\r\n" + "			      \"type\"  : \"home\",\r\n"
			+ "			      \"number\": \"0123-4567-8910\"\r\n" + "			    }\r\n" + "			  ]\r\n"
			+ "			}";

	@Test
	@Deployment(resources = { "JsonPath.bpmn" })
	public void teste1() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("json", json);

		ProcessInstance processInstance = processEngineRule.getRuntimeService().startProcessInstanceByKey("JsonPath",
				variables);

		HistoricProcessInstance historicProcessInstance = processEngineRule.getHistoryService()
				.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		assertNotNull(historicProcessInstance.getEndTime());

		assertThat(processInstance).isStarted().hasPassed("PrintJson").hasVariables("MyJsonPath")
				.hasPassed("PrintHello").hasNotPassed("printafter");

		assertThat(processInstance)
				.hasPassedInOrder("startEvent", "PrintJson", "gatewayStart", "PrintHello", "gatewayEnd", "endEvent")
				.isEnded();

	}

	@Test
	@Deployment(resources = { "assigneTimerUserTaskListener.bpmn" })
	public void teste2() {
		ProcessInstance processInstance = processEngineRule.getRuntimeService()
				.startProcessInstanceByKey("assigneTimerUserTaskListener");

		assertThat(processInstance).task("firsttask").isAssignedTo("outro");
		List<Task> tasks = processEngineRule.getTaskService().createTaskQuery().taskName("firsttask").list();
		System.out.println("PRINT " + tasks.size());
		assertEquals(1, tasks.size());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("name", "joao");
		
		assertThat(processInstance).isStarted().hasNotPassed("firsttask").hasNotPassed("secondtask");
		processEngineRule.getTaskService().complete(tasks.get(0).getId(), variables);
		assertThat(processInstance).isStarted().hasPassed("firsttask").isWaitingAt("secondtask").task().isAssignedTo("outro");
	
		complete(task());
		assertThat(processInstance).isEnded();
	}
	@Test
	@Deployment(resources = { "userTakEsclusiveGatewayMessageTimerEvent.bpmn" })
	public void teste3() {
		ProcessInstance processInstance = processEngineRule.getRuntimeService().startProcessInstanceByKey("userTakEsclusiveGatewayMessageTimerEvent");

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("num", 6);

		assertThat(processInstance).task("dosomething").hasCandidateGroup("management");

		List<Task> tasks = processEngineRule.getTaskService().createTaskQuery().taskCandidateGroup("management").list();
		System.out.println("Print " + tasks.size());
		assertEquals(1, tasks.size());
		assertEquals("dosomething", tasks.get(0).getTaskDefinitionKey());

		processEngineRule.getTaskService().complete(tasks.get(0).getId(), variables);

		processEngineRule.getRuntimeService().createMessageCorrelation("MSG_SOMETHING").processInstanceId(processInstance.getId())
				.correlate();

		assertThat(processInstance).isEnded().hasPassed("endEvent1");
	}

	@Test
	@Deployment(resources = { "userTakEsclusiveGatewayMessageTimerEvent.bpmn" })
	public void teste4() {
		ProcessInstance processInstance = processEngineRule.getRuntimeService().startProcessInstanceByKey("userTakEsclusiveGatewayMessageTimerEvent");

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("num", 3);

		assertThat(processInstance).task("dosomething").hasCandidateGroup("management");

		List<Task> tasks = processEngineRule.getTaskService().createTaskQuery().taskCandidateGroup("management").list();
		System.out.println("Print " + tasks.size());
		assertEquals(1, tasks.size());
		assertEquals("dosomething", tasks.get(0).getTaskDefinitionKey());

		processEngineRule.getTaskService().complete(tasks.get(0).getId(), variables);

		Job job = processEngineRule.getManagementService().createJobQuery().timers().singleResult();
		processEngineRule.getManagementService().executeJob(job.getId());

		assertThat(processInstance).isEnded().hasPassed("endEvent2");
	}
}
