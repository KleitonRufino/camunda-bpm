package org.camunda.bpm.spring.boot.example.model;

import java.io.Serializable;

import com.jayway.jsonpath.JsonPath;

public class MyJsonPath implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3997253119207317688L;

	public static String read(String json, String expression) {
		return JsonPath.read(json, expression);
	}
}
