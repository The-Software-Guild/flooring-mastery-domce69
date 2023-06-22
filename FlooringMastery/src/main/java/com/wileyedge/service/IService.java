package com.wileyedge.service;

import java.math.BigDecimal;
import java.util.List;

import com.wileyedge.exception.IncorrectDateInputException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.Tax;

public interface IService {
	
	//-----IMPORT-----\\
	public List<Order> importOrders(String orderDate);
	
	public List<Tax> importTax();
	
	public List<Product> importProducts();
	
	//-----FUCNTIONALITIES-----\\
	
	public Order newOrder(int orderNum, String custName, String state, String prodT, BigDecimal area);
	
	public void updateOrder(Order o, String orderDate);
	
	public void deleteOrder(Order o, String orderDate);
	
	public void calcOrder(Order o);
	
	public void saveOrder(Order o, String orderDate);
	
	public Order findOrder(List<Order> orders, int orderNumber);
	
	public Order findByOrderNumber(String orderDate, int on);
	
	public void exportAll();
	
	public int generateOrderNumber();
	//-----VALIDATION-----\\
	
	public Boolean checkName(String name);
	
	public Boolean checkState(String state, List<Tax> taxes);
	
	public Boolean checkDate(String orderDate);
	
	public Boolean checkProduct(String product, List<Product> products);
	
	public Boolean checkArea(BigDecimal area);
	
	public boolean checkOrderNumber(List<Order> orders, int on);
	
	public String formatOrderDate(String od);
}
