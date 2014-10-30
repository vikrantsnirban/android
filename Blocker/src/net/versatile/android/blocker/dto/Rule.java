package net.versatile.android.blocker.dto;

public class Rule {
	String ruleCriteria;
	String ruleExpression;
	
	public Rule(){
		
	}
	
	public Rule(String ruleCriteria, String ruleExpression){
		this.ruleCriteria = ruleCriteria;
		this.ruleExpression = ruleExpression;
	}
	
	public String getRuleCriteria() {
		return ruleCriteria;
	}
	public void setRuleCriteria(String ruleCriteria) {
		this.ruleCriteria = ruleCriteria;
	}
	public String getRuleExpression() {
		return ruleExpression;
	}
	public void setRuleExpression(String ruleExpression) {
		this.ruleExpression = ruleExpression;
	}

	@Override
	public String toString() {
		return ruleCriteria + ":" + ruleExpression;
	}
}
