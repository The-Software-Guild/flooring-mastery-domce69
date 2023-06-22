package com.wileyedge.dao;

import java.math.BigDecimal;
import java.util.List;

import com.wileyedge.exception.IncorrectDateInputException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.Tax;

public interface IDao {
	//-----IMPORT DATA-----\\
	public List<Order> importOrders(String orderDate);
	
	public List<Tax> importTax();
	
	public List<Product> importProducts();
	
	//-----SAVE ORDERS-----\\
	public void saveOrders(List<Order> orders, String orderDate);

	//-----UPDATE ORDERS-----\\
	public void updateOrder(Order order, String orderDate);
	
	//-----DELETE ORDERS-----\\
	public void deleteOrder(Order order, String orderDate);
	
	//-----VALIDATION-----\\
	
	public Boolean isNameValid(String name);
	
	public Boolean isStateValid(String state, List<Tax> taxes);
	
	public Boolean isDateValid(String orderDate);
	
	public Boolean isProductValid(String product, List<Product> products);
	
	public Boolean isAreaValid(BigDecimal area);
	
	public boolean checkOrderNumber(List<Order> order, int on);
	
	//-----GENERATING-----\\
	
	public int generateOrderNumber();
}
