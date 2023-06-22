package com.wileyedge.UserIo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.wileyedge.exception.IncorrectDateInputException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.Tax;
import com.wileyedge.service.IService;

public class View {
	
	public static Scanner scanner = new Scanner(System.in);
	
	public int mainMenu() {
		
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("* <<Flooring Program>>");
		System.out.println("* 1. Display Orders");
		System.out.println("* 2. Add an Order");
		System.out.println("* 3. Edit an Order");
		System.out.println("* 4. Remove an Order");
		System.out.println("* 5. Export All Data");
		System.out.println("* 6. Quit");
		System.out.println("*");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		
		return scanner.nextInt();
	}
	
	public void displayOrdersIo(List<Order> orders, String orderDate) {
		System.out.println("* * * Printing Orders For Date " + orderDate + "... * * *");
		for(Order o: orders) {
			System.out.println(o);
		}
	}
	
	public void displayProducts(List<Product> products) {
		for(Product p: products) {
			System.out.println("Product: " + p.getProducttype() + " Cost per sq ft: $" + p.getCostpersquarefoot() + " Labour cost per sq ft: $" + p.getLaborcostpersquarefoot());
		}
	}
	
	public String[] addOrder(IService service) {
		
		List<Product> products = service.importProducts();
		List<Tax> taxes = service.importTax();
		
		System.out.println("Enter an order date in the format of (MM-DD-YYYY): ");
		scanner.nextLine();
		String date = scanner.nextLine();
		
		while(!service.checkDate(date)) {
			System.out.println("Enter an order date in the format of (MM-DD-YYYY): ");
			date = scanner.nextLine();
		}
		
		System.out.println("Enter a customer name: ");
		String name = scanner.nextLine();
		
		while(!service.checkName(name)) {
			System.out.println("Enter a customer name: ");
			name = scanner.nextLine();
		}
		
		System.out.println("Enter a state: ");
		String state = scanner.nextLine();
		
		while(!service.checkState(state, taxes)) {
			System.out.println("Valid States:\n");
			for(Tax t: taxes) {
				System.out.println(t.getStatename());
			}
			System.out.println("Enter a state: ");
			state = scanner.nextLine();
		}
		
		displayProducts(products);
		
		System.out.println("Enter a product: ");
		String product = scanner.nextLine();
		
		while(!service.checkProduct(product, products)) {
			System.out.println("Enter a product: ");
			product = scanner.nextLine();
		}
		
		System.out.println("Enter an area greater than 100: ");
		BigDecimal area = new BigDecimal(scanner.next());
		
		while(!service.checkArea(area)) {
			System.out.println("Enter an area greater than 100: ");
			area = new BigDecimal(scanner.next());
		}
		
		if(service.checkName(name)) {
			if(service.checkState(state, taxes)) {
				if(service.checkProduct(product, products)) {
					if(service.checkArea(area)) {
						
						String str = area.toString();
						String[] arr = {date, name, state, product, str};
						return arr;
					}
				}
			}
		}
		
		return null;
	}
	
	public Boolean orderSummary(Order o) {
		System.out.println("Order Summary");
		System.out.println(o);
		System.out.println("Would you like to place this order? (Y/N)");
		String choice = scanner.next();
		if(choice.equalsIgnoreCase("y")) {
			return true;
		}
		return false;
	}
	
	public Boolean editOrderSummary(Order o) {
		System.out.println("Edit Order Summary");
		System.out.println(o);
		System.out.println("Would you like to save your changes? (Y/N)");
		String choice = scanner.next();
		if(choice.equalsIgnoreCase("y")) {
			return true;
		}
		return false;
	}
	
	public Order editOrder(IService service, Order o) {
		
		List<Product> products = service.importProducts();
		List<Tax> taxes = service.importTax();
	
		System.out.println("Enter a customer name (" + o.getCustomername() +"): ");
		scanner.nextLine();
		String name = scanner.nextLine();
		if(name.isBlank()) {
			name = o.getCustomername();
		}
		while(!service.checkName(name)) {
			System.out.println("Enter a customer name (" + o.getCustomername() +"): ");
			name = scanner.nextLine();
			if(name.isBlank()) {
				name = o.getCustomername();
			}
		}
		
		
		System.out.println("Enter a state (" + o.getState() +"): ");
		String state = scanner.nextLine();
		if(state.isBlank()) {
			state = o.getState();
		}
		while(!service.checkState(state, taxes)) {
			System.out.println("Enter a state (" + o.getState() +"): ");
			state = scanner.nextLine();
			if(state.isBlank()) {
				state = o.getState();
			}
		}
		
		displayProducts(products);
		
		System.out.println("Enter a product (" + o.getProducttype() +"): ");
		String product = scanner.nextLine();
		if(product.isBlank()) {
			product = o.getProducttype();
		}
		while(!service.checkProduct(product, products)) {
			System.out.println("Enter a product (" + o.getProducttype() +"): ");
			product = scanner.nextLine();
			if(product.isBlank()) {
				product = o.getProducttype();
			}
		}
		
		System.out.println("Enter an area greater than 100(" + o.getArea() +"): ");
		BigDecimal area = new BigDecimal(scanner.next());
		if(area.equals("")) {
			area = o.getArea();
		}
		while(!service.checkArea(area)) {
			System.out.println("Enter an area greater than 100(" + o.getArea() +"): ");
			area = new BigDecimal(scanner.next());
			if(area.equals("")) {
				area = o.getArea();
			}
		}
		
		Order newO = o;
		newO.setCustomername(name);
		newO.setState(state);
		newO.setProducttype(product);
		newO.setArea(area);
		return newO;
		
	}
	
	
}
