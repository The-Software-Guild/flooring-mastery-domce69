package com.wileyedge.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.wileyedge.dao.DaoImp;
import com.wileyedge.dao.IDao;
import com.wileyedge.exception.NoOrdersFoundException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.Tax;

public class Service implements IService {

	@Autowired
	private IDao dao;
	private List<Tax> taxes;
	private List<Product> products;
	private HashMap<String, List<Order>> orders = new HashMap<String, List<Order>>();
	
	public String formatOrderDate(String od) {
		String d = od.substring(0,2);
		String m = od.substring(3,5);
		String y = od.substring(6,10);
		od = d + m + y;
		return od;
	}
	
	@Override
	public List<Order> importOrders(String orderDate) {
		List<Order> order = new ArrayList<Order>();
		String date = formatOrderDate(orderDate);
		
		if(orders.containsKey(date)) {
			order = orders.get(date);
			return order;
			
		} else {
			
			try {
				
				order = dao.importOrders(date);
				
				if(order.isEmpty()) {
					NoOrdersFoundException non = new NoOrdersFoundException("No orders found for date '" + orderDate + "'!");
					throw non;
				}
			} catch (NoOrdersFoundException non) {	
				System.out.println("ERROR: " + non.getMsg());
			}
		}
		
		return order;
	}

	public Order findOrder(List<Order> orders, int orderNumber) {
		for(Order o: orders) {
			if(o.getOrdernumber() == orderNumber) {
				return o;
			}
		}
		
		return null;
	}
	
	@Override
	public List<Tax> importTax() {
		taxes = dao.importTax();
		return taxes;
	}

	@Override
	public List<Product> importProducts() {
		products = dao.importProducts();
		return products;
	}

	@Override
	public Order newOrder(int orderNum, String custName, String state, String prodT, BigDecimal area) {
		Order o = new Order(orderNum, custName, state, prodT, area);
		calcOrder(o);
		return o;
	}

	@Override
	public void updateOrder(Order o, String orderDate) {
		String date = formatOrderDate(orderDate);
		dao.updateOrder(o, date);
		saveOrder(o, orderDate);
	}

	@Override
	public void deleteOrder(Order o, String orderDate) {
		String date = formatOrderDate(orderDate);
		deleteOrderFromList(orderDate, o.getOrdernumber());
		dao.deleteOrder(o, date);
	}

	@Override
	public void calcOrder(Order o) {
		
		for(Product p: products) {
			if(p.getProducttype().equalsIgnoreCase(o.getProducttype())) {
				o.setCostpersquarefoot(p.getCostpersquarefoot());
				o.setLabourcostpersquarefoot(p.getLaborcostpersquarefoot());
			}
		}
		
		for(Tax t: taxes) {
			if(t.getStatename().equalsIgnoreCase(o.getState())) {
				o.setTaxrate(t.getTaxrate());
			}
		}
		
		o.calcMaterials();
		o.calcLabour();
		o.calcTax();
		o.calcTotal();
	}

	public void saveOrder(Order o, String orderDate) {
		
		orderDate = formatOrderDate(orderDate);
		
		if(orders.containsKey(orderDate)) {
			List<Order> list = orders.get(orderDate);
			for(Order old: list) {
				if(old.getOrdernumber() == o.getOrdernumber()) {
					list.remove(old);
					list.add(o);
				}
			}
			
			orders.get(orderDate).equals(list);
			
		} else {
			List<Order> list = new ArrayList<Order>();
			list.add(o);
			orders.put(orderDate, list);
		}
	}
	
	public void deleteOrderFromList(String orderDate, int on) {
		
		orderDate = formatOrderDate(orderDate);
		
		if(orders.containsKey(orderDate)) {
			List<Order> list = orders.get(orderDate);
			for(Order o: list) {
				if(o.getOrdernumber() == on) {
					list.remove(o);
					break;
				}
			}
			orders.get(orderDate).equals(list);
		}
	}
	
	public Order findByOrderNumber(String orderDate, int on) {
		List<Order> orders = importOrders(orderDate);
		for(Order o: orders) {
			if(o.getOrdernumber() == on) {
				return o;
			}
		}
		return null;
	}
	
	public void exportAll() {
		for(Entry<String, List<Order>> entry: orders.entrySet()) {
			String orderDate = entry.getKey();
			List<Order> list = entry.getValue();
			dao.saveOrders(list, orderDate);
		}
	}
	
	@Override
	public Boolean checkName(String name) {
		return dao.isNameValid(name);
	}

	@Override
	public Boolean checkState(String state, List<Tax> taxes) {
		return dao.isStateValid(state, taxes);
	}

	@Override
	public Boolean checkDate(String orderDate) {
		return dao.isDateValid(orderDate);
	}

	@Override
	public Boolean checkProduct(String product, List<Product> products) {
		return dao.isProductValid(product, products);
	}

	@Override
	public Boolean checkArea(BigDecimal area) {
		
		return dao.isAreaValid(area);
	}

	@Override
	public int generateOrderNumber() {
		return dao.generateOrderNumber() + 1;
	}

	@Override
	public boolean checkOrderNumber(List<Order> order, int on) {
		
		return dao.checkOrderNumber(order, on);
	}

}
