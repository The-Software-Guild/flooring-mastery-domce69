  package com.wileyedge.model;

import java.math.BigDecimal;

public class Product {
	private String producttype;
	private BigDecimal costpersquarefoot;
	private BigDecimal laborcostpersquarefoot;
	public Product(String producttype, BigDecimal costpersquarefoot, BigDecimal laborcostpersquarefoot) {
		super();
		this.producttype = producttype;
		this.costpersquarefoot = costpersquarefoot;
		this.laborcostpersquarefoot = laborcostpersquarefoot;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public BigDecimal getCostpersquarefoot() {
		return costpersquarefoot;
	}
	public void setCostpersquarefoot(BigDecimal costpersquarefoot) {
		this.costpersquarefoot = costpersquarefoot;
	}
	public BigDecimal getLaborcostpersquarefoot() {
		return laborcostpersquarefoot;
	}
	public void setLaborcostpersquarefoot(BigDecimal laborcostpersquarefoot) {
		this.laborcostpersquarefoot = laborcostpersquarefoot;
	}
	@Override
	public String toString() {
		return "\nProduct\nProduct Type: " + producttype + "\nCost per sq ft: " + costpersquarefoot
				+ "\nLabour cost per sq ft: " + laborcostpersquarefoot;
	}
	
}
