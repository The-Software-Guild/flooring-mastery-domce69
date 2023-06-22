package com.wileyedge.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.wileyedge.exception.IncorrectDateInputException;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.Tax;

public class DaoImp implements IDao {

	private String orderFile = "C:\\C353\\SampleFileData\\Orders\\Orders_";
	private String ordersFolder = "C:\\C353\\SampleFileData\\Orders";
	private String taxFile = "C:\\C353\\SampleFileData\\Data\\Taxes.txt";
	private String productFile = "C:\\C353\\SampleFileData\\Data\\Products.txt";
	String properties;
	int orderNumb;

	private BufferedReader br = null;
	private BufferedWriter bw = null;

	public List<Order> importOrders(String orderDate) {
		List<Order> orders = new ArrayList<Order>();
		String of = orderFile + orderDate + ".txt";

		try {
			br = new BufferedReader(new FileReader(of));
			properties = br.readLine();
			String line = br.readLine();

			while (line != null) {
				String[] arr = line.split(",");
				List<String> list = new ArrayList<String>(Arrays.asList(arr));

				while(list.size() >= 13) {
					String name = list.get(1) + "," + list.get(2);
					list.remove(1);
					list.remove(1);	
					list.add(1, name);			
				} 
				
				arr = list.toArray(new String[0]);
				
				Order o = new Order(Integer.parseInt(arr[0]), arr[1], arr[2], new BigDecimal(arr[3]), arr[4],
						new BigDecimal(arr[5]), new BigDecimal(arr[6]), new BigDecimal(arr[7]), new BigDecimal(arr[8]),
						new BigDecimal(arr[9]), new BigDecimal(arr[10]), new BigDecimal(arr[11]));
				orders.add(o);
				line = br.readLine();
			}

			br.close();  
		} catch (IOException e) {

		}
		return orders;
	}

	public List<Tax> importTax() {
		List<Tax> taxes = new ArrayList<Tax>();

		try {
			br = new BufferedReader(new FileReader(taxFile));
			String str = br.readLine();
			String line = br.readLine();

			while (line != null) {
				String[] arr = line.split(",");
				List<String> list = new ArrayList<String>(Arrays.asList(arr));

				arr = list.toArray(new String[0]);
				Tax t = new Tax(arr[0], arr[1], new BigDecimal(arr[2]));
				taxes.add(t);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {

		}
		return taxes;
	}

	public List<Product> importProducts() {
		List<Product> products = new ArrayList<Product>();

		try {
			br = new BufferedReader(new FileReader(productFile));
			String str = br.readLine();
			String line = br.readLine();

			while (line != null) {
				String[] arr = line.split(",");
				List<String> list = new ArrayList<String>(Arrays.asList(arr));

				arr = list.toArray(new String[0]);
				Product p = new Product(arr[0], new BigDecimal(arr[1]), new BigDecimal(arr[2]));
				products.add(p);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {

		}
		return products;
	}


	public void saveOrders(List<Order> orders, String orderDate) {
		String of = orderFile + orderDate + ".txt";

		try {

			bw = new BufferedWriter(new FileWriter(of));
			bw.write(properties + "\r\n");

			for (Order o : orders) {
				String write = Integer.toString(o.getOrdernumber()) + ",";
				write += o.getCustomername() + ",";
				write += o.getState() + ",";
				write += o.getTaxrate().toString() + ",";
				write += o.getProducttype() + ",";
				write += o.getArea().toString() + ",";
				write += o.getCostpersquarefoot().toString() + ",";
				write += o.getLabourcostpersquarefoot().toString() + ",";
				write += o.getMaterialcost().toString() + ",";
				write += o.getLabourcost().toString() + ",";
				write += o.getTax().toString() + ",";
				write += o.getTotal().toString() + ",";

				bw.write(write + "\r\n");
			}

			bw.flush();
			bw.close();

		} catch (IOException e) {
			System.out.println("Error");
		}

	}

	public void updateOrder(Order order, String orderDate) {
		List<Order> orders = importOrders(orderDate);
		for (Order o : orders) {
			if (o.getOrdernumber() == order.getOrdernumber()) {
				orders.remove(o);
				orders.add(order);
				break;
			}
		}
		saveOrders(orders, orderDate);
	}

	public void deleteOrder(Order order, String orderDate) {
		List<Order> orders = importOrders(orderDate);
		orders.removeIf((o) -> o.getOrdernumber() == order.getOrdernumber());
		saveOrders(orders, orderDate);
	}

	@Override
	public Boolean isNameValid(String name) {
		if (name.isEmpty()) {
			System.out.println("Error, name cannot be blank");
			return false;
		}

		for (int i = 0; i < name.length(); i++) {
			if ((Character.isLetterOrDigit(name.charAt(i)) == false)) {
				if (name.charAt(i) != ',' && name.charAt(i) != '.' && !Character.isWhitespace(name.charAt(i))) {
					System.out.println("Error, name contains illegal characters");
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public Boolean isStateValid(String state, List<Tax> taxes) {
		for (Tax t : taxes) {
			if (state.equalsIgnoreCase(t.getStatename())) {
				return true;
			}
		}
		System.out.println("Error, we do not deliver to this state");
		return false;
	}

	@Override
	public Boolean isDateValid(String orderDate) {
		if(orderDate.length() < 10) {
			System.out.println("Error: Incorrect date format");
			return false;
		} else {
			String m = orderDate.substring(0, 2);
			String d = orderDate.substring(3, 5);
			String y = orderDate.substring(6, 10);
			orderDate = m + d + y;
			
			LocalDate od = LocalDate.of(Integer.parseInt(y), Integer.parseInt(m), Integer.parseInt(d));
			LocalDate today = LocalDate.now();
			
			if(od.isAfter(today)) {
				return true;
			} else {
				System.out.println("Error: Date must be in the future");
				return false;
			}
			
		}
	}

	@Override
	public Boolean isProductValid(String product, List<Product> products) {

		for (Product p : products) {
			if (p.getProducttype().equalsIgnoreCase(product)) {
				return true;
			}
		}
		System.out.println("Error, this product is not listed");
		return false;
	}

	@Override
	public Boolean isAreaValid(BigDecimal area) {
		if (area.compareTo(new BigDecimal("100")) >= 0) {
			return true;
		}
		System.out.println("Error, area must be greater than 100");
		return false;
	}
	
	public boolean checkOrderNumber(List<Order> orders, int on) {
		
		for (Order o : orders) {
			if (o.getOrdernumber() == on) {

				return true;
			}
		}
	return false;
}

	@Override
	public int generateOrderNumber() {
		orderNumb = 0;

		File fold = new File(ordersFolder);
		File[] files = fold.listFiles();

		for (File f : files) {
			f.getName();
			try {
				br = new BufferedReader(new FileReader(f.getPath()));
				String properties = br.readLine();
				String line = br.readLine();

				while (line != null) {
					
					String[] arr = line.split(",");
					int on = Integer.parseInt(arr[0]);
					if (on > orderNumb) {
						orderNumb = on;
					}
					line = br.readLine();
				}

				br.close();
				
			} catch (IOException e) {

			}
			
		}
		return orderNumb;
	}
}
