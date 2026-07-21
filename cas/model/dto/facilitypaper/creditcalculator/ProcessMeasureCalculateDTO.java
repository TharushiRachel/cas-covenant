package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

import java.util.Map;

public class ProcessMeasureCalculateDTO {
	private Map<String, Double> argumentMap;
    private String expression;
    private String result;
    
	public Map<String, Double> getArgumentMap() {
		return argumentMap;
	}
	public void setArgumentMap(Map<String, Double> argumentMap) {
		this.argumentMap = argumentMap;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
