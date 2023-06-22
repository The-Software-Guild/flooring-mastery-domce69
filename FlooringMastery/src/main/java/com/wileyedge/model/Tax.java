package com.wileyedge.model;

import java.math.BigDecimal;

public class Tax {
	
	private String stateabbreviation;
	private String statename;
	private BigDecimal taxrate;
	
	public Tax(String stateabbreviation, String statename, BigDecimal taxrate) {
		super();
		this.stateabbreviation = stateabbreviation;
		this.statename = statename;
		this.taxrate = taxrate;
	}
	public String getStateabbreviation() {
		return stateabbreviation;
	}
	public void setStateabbreviation(String stateabbreviation) {
		this.stateabbreviation = stateabbreviation;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public BigDecimal getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}
	
	@Override
	public String toString() {
		return "\nTax\nState Abbreviation: " + stateabbreviation + "\nState Name: " + statename + "\nTax Rate: " + taxrate;
	}
	
	
}
