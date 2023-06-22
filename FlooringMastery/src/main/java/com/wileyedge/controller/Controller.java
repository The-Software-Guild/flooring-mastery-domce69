package com.wileyedge.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wileyedge.UserIo.View;
import com.wileyedge.dao.DaoImp;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.Tax;
import com.wileyedge.service.IService;
import com.wileyedge.service.Service;

public class Controller {
	
	public static void run(View io, IService service) {
		
		Scanner userIn = new Scanner(System.in);
		
		boolean terminate = false;
		while(true) {
			
			int i = io.mainMenu(); 
		
			switch(i) {
			case 1:{
				System.out.println("Enter an order date in the format of (MM-DD-YYYY): ");
				String od = userIn.next();
				io.displayOrdersIo(service.importOrders(od), od);
				break;
			}
			case 2:{
				String[] order = io.addOrder(service);
				String orderDate = order[0];
				
				//create order numb properly
				int num = service.generateOrderNumber();
				Order o = service.newOrder(num, order[1], order[2], order[3], new BigDecimal(order[4]));
				
				if(io.orderSummary(o)) {
					service.saveOrder(o, orderDate);
					System.out.println("Order Placed");
				} else {
					System.out.println("Order not processed");
				}
				
				break;
			}
			case 3:{
				System.out.println("Enter the order date you would like to edit in the format of (MM-DD-YYYY): ");
				String od = userIn.next();
				while(!service.checkDate(od)) {
					System.out.println("Error: order date does not exist!");
					System.out.println("Enter the order date you would like to edit in the format of (MM-DD-YYYY): ");
					od = userIn.next();
				}
				System.out.println("Enter the order number you would like to edit: ");
				int on = userIn.nextInt();
				List<Order> ord = service.importOrders(od);
				while(!service.checkOrderNumber(ord, on)) {
					System.out.println("Error: order number does not exist!");
					System.out.println("Enter the order number you would like to edit: ");
					on = userIn.nextInt();
				}
				
				Order o = service.findOrder(ord, on);
				
				io.editOrder(service, o);
				Order o1 = service.newOrder(o.getOrdernumber(), o.getCustomername(), o.getState(), o.getProducttype(), o.getArea());
				
				if(io.editOrderSummary(o1)) {
					service.updateOrder(o1, od);
					System.out.println("Order Updated");
				} else {
					System.out.println("Order not updated");
				}
				
				break;
			}
			case 4:{
				System.out.println("Enter the order date you would like to delete in the format of (MM-DD-YYYY): ");
				String od = userIn.next();
				System.out.println("Enter the order number you would like to delete: ");
				int on = userIn.nextInt();
				Order o = service.findByOrderNumber(od, on);
				service.deleteOrder(o, od);
				break;
			}
			case 5:{
				service.exportAll();
				break;
			}

			case 6:{
				terminate = true;
				break;
			}
		}
			if (terminate) {
				System.out.println("Exiting app, ciao!");
				break;
			}
	} 
		
}
	
	public static void main(String[] args) {
		
		View io = new View();
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		IService service = (IService) context.getBean("service", Service.class);
		
		run(io, service);
		
	}
	
}
