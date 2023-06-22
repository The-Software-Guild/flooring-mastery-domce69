package com.wileyedge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Order {
	
	private Integer ordernumber;
	private String customername;
	private String state;
	private BigDecimal taxrate;
	private String producttype;
	private BigDecimal area;
	private BigDecimal costpersquarefoot;
	private BigDecimal labourcostpersquarefoot;
	private BigDecimal materialcost;
	private BigDecimal labourcost;
	private BigDecimal tax;
	private BigDecimal total;
	
	public Order(Integer ordernumber, String customername, String state, BigDecimal taxrate, String producttype,
			BigDecimal area, BigDecimal costpersquarefoot, BigDecimal labourcostpersquarefoot, BigDecimal materialcost,
			BigDecimal laborcost, BigDecimal tax, BigDecimal total) {
		super();
		this.ordernumber = ordernumber;
		this.customername = customername;
		this.state = state;
		this.taxrate = taxrate;
		this.producttype = producttype;
		this.area = area;
		this.costpersquarefoot = costpersquarefoot;
		this.labourcostpersquarefoot = labourcostpersquarefoot;
		this.materialcost = materialcost;
		this.labourcost = laborcost;
		this.tax = tax;
		this.total = total;
	}
	
	public Order(int ordernumber, String customername, String state, String producttype, BigDecimal area) {
		this.ordernumber = ordernumber;
		this.customername = customername;
		this.state = state;
		this.producttype = producttype;
		this.area = area;
	}
	public int getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	public BigDecimal getCostpersquarefoot() {
		return costpersquarefoot;
	}
	public void setCostpersquarefoot(BigDecimal costpersquarefoot) {
		this.costpersquarefoot = costpersquarefoot;
	}
	public BigDecimal getLabourcostpersquarefoot() {
		return labourcostpersquarefoot;
	}
	public void setLabourcostpersquarefoot(BigDecimal labourcostpersquarefoot) {
		this.labourcostpersquarefoot = labourcostpersquarefoot;
	}
	public BigDecimal getMaterialcost() {
		return materialcost;
	}
	public void setMaterialcost(BigDecimal materialcost) {
		this.materialcost = materialcost;
	}
	public BigDecimal getLabourcost() {
		return labourcost;
	}
	public void setLabourcost(BigDecimal laborcost) {
		this.labourcost = laborcost;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public void calcMaterials() {
		this.materialcost = this.area.multiply(this.costpersquarefoot).setScale(2, RoundingMode.HALF_UP);
	}
	
	public void calcLabour() {
		this.labourcost = this.area.multiply(this.labourcostpersquarefoot).setScale(2, RoundingMode.HALF_UP);
	}
	
	public void calcTax() {
		BigDecimal t = (this.taxrate.divide(new BigDecimal("100"))).multiply(this.materialcost.add(this.labourcost));
		this.tax = t.setScale(0, RoundingMode.HALF_UP);
	}
	
	public void calcTotal() {
		this.total = this.materialcost.add(this.labourcost.add(this.tax)).setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public String toString() {
		return "\nOrder\nOrder Number: " + ordernumber + "\nCustomer Name: " + customername + "\nState: " + state
				+ "\nTax Rate: " + taxrate + "\nProduct Type: " + producttype + "\nArea: " + area + "\nCost Per SQ FT: "
				+ costpersquarefoot + "\nLabour Cost Per SQ FT: " + labourcostpersquarefoot + "\nMaterial Cost: "
				+ materialcost + "\nLabour Cost: " + labourcost + "\nTax: " + tax + "\nTotal: " + total + "\n";
	}
	
	
}
