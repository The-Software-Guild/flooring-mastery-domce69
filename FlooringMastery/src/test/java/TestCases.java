import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.wileyedge.dao.IDao;
import com.wileyedge.model.Order;
import com.wileyedge.model.Product;
import com.wileyedge.model.Tax;
import com.wileyedge.dao.DaoImp;
import com.wileyedge.service.IService;
import com.wileyedge.service.Service;

public class TestCases {
	
	private IDao dao = null;
	private IService service = null;
	
	@BeforeAll
	static void openTests() throws Exception {
		System.out.println("Test Starting");
	}
	
	@AfterAll
	static void closeTests() throws Exception {
		System.out.println("Tests finished");
	}
	
	@Test
	void importOrdersTest() {
		dao = new DaoImp();
			String od = "06012013";
		
			List<Order> res = dao.importOrders(od);
			
			List<Order> testRes = new ArrayList<>();
			Order o = new Order(1,"Ada Lovelace","CA",new BigDecimal("25.00"),"Tile",new BigDecimal("249.00"),new BigDecimal("3.50"),new BigDecimal("4.15"),new BigDecimal("871.50"),new BigDecimal("1033.35"),new BigDecimal("476.21"),new BigDecimal("2381.06"));
			testRes.add(o);
			assertEquals(testRes.get(0).toString(), res.get(0).toString());
	}
	
	@Test
	void importTaxesTest() {
		dao = new DaoImp();
		List<Tax> daoTaxes = dao.importTax();
		
		Tax t1 = new Tax("WA", "Washington", new BigDecimal("9.25"));
		Tax t2 = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
		Tax t3 = new Tax("CA", "Calfornia", new BigDecimal("25.00"));
		Tax t4 = new Tax("TX", "Texas", new BigDecimal("4.45"));
		
		List<Tax> testTaxes = new ArrayList<>();
		testTaxes.add(t4);
		testTaxes.add(t1);
		testTaxes.add(t2);
		testTaxes.add(t3);
		
		assertEquals(testTaxes.toString(), daoTaxes.toString());
	}
	
	@Test
	void importProductsTest() {
		dao = new DaoImp();
		List<Product> daoProducts = dao.importProducts();
		
		Product p1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
		Product p2 = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
		Product p3 = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
		Product p4 = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
		
		List<Product> testProducts = new ArrayList<>();
		testProducts.add(p1);
		testProducts.add(p2);
		testProducts.add(p3);
		testProducts.add(p4);
		
		assertEquals(testProducts.toString(), daoProducts.toString());
	}
	
	@Test
	void saveOrderTest() {
		service = new Service();
		String od = "06-01-2013";
		Order order = new Order(1,"Ada Lovelace","CA",new BigDecimal("25.00"),"Tile",new BigDecimal("249.00"),new BigDecimal("3.50"),new BigDecimal("4.15"),new BigDecimal("871.50"),new BigDecimal("1033.35"),new BigDecimal("476.21"),new BigDecimal("2381.06"));
		assertDoesNotThrow(() -> service.saveOrder(order, od));
	}
	
	@Test
	void editOrderTest() {
		dao = new DaoImp();
		String od = "06012013";
		Order order = new Order(1,"Ada Lovelace","CA",new BigDecimal("25.00"),"Tile",new BigDecimal("249.00"),new BigDecimal("3.50"),new BigDecimal("4.15"),new BigDecimal("871.50"),new BigDecimal("1033.35"),new BigDecimal("476.21"),new BigDecimal("2381.06"));
		assertDoesNotThrow(() -> dao.updateOrder(order, od));
	}
	
	@Test //Deleting actual sample record.
	void deleteOrderTest() {
		dao = new DaoImp();
		String orderDate = "06012013";
		Order order = new Order(1,"Ada Lovelace","CA",new BigDecimal("25.00"),"Tile",new BigDecimal("249.00"),new BigDecimal("3.50"),new BigDecimal("4.15"),new BigDecimal("871.50"),new BigDecimal("1033.35"),new BigDecimal("476.21"),new BigDecimal("2381.06"));

		assertDoesNotThrow(() -> dao.deleteOrder(order, orderDate));
	}
	
	@Test
	void getMaxOrderNumberTest() {
		dao = new DaoImp();
		int daoInt = dao.generateOrderNumber();
		int testInt = 4;
		assertEquals(daoInt, testInt);
	}
	
	@Test
	void checkNameTest() {
		dao = new DaoImp();
		assertTrue(dao.isNameValid("Thomas Andraos"));
		assertTrue(dao.isNameValid("Thomas, J Andraos"));
		
		assertFalse(dao.isNameValid("Thomas=23/"));
		assertFalse(dao.isNameValid("12-34"));
		assertFalse(dao.isNameValid(""));
	}
	
	@Test
	void checkDateTest() {
		dao = new DaoImp();
		assertFalse(dao.isDateValid("11-11-2010"));
		assertFalse(dao.isDateValid("11-13-2022"));
		
		assertTrue(dao.isDateValid("11-11-2023"));
		assertTrue(dao.isDateValid("07-08-2025"));
	}
	

	@Test
	void checkAreaTest() {
		dao = new DaoImp();
		assertTrue(dao.isAreaValid(new BigDecimal("1000")));
		assertTrue(dao.isAreaValid(new BigDecimal("364")));
		
		assertFalse(dao.isAreaValid(new BigDecimal("50")));
		assertFalse(dao.isAreaValid(new BigDecimal("90")));
	}
	
	@Test
	void checkOrderNumberTest() {
		dao = new DaoImp();
		List<Order> orders = new ArrayList<>();
		Order or1 = new Order(1,"Thomas","Texas",new BigDecimal("25.00"),"Tile",new BigDecimal("249.00"),new BigDecimal("3.50"),new BigDecimal("4.15"),new BigDecimal("871.50"),new BigDecimal("1033.35"),new BigDecimal("476.21"),new BigDecimal("2381.06"));
		Order or2 = new Order(2,"Thomas","Texas",new BigDecimal("25.00"),"Tile",new BigDecimal("249.00"),new BigDecimal("3.50"),new BigDecimal("4.15"),new BigDecimal("871.50"),new BigDecimal("1033.35"),new BigDecimal("476.21"),new BigDecimal("2381.06"));
		Order or3 = new Order(3,"Thomas","Texas",new BigDecimal("25.00"),"Tile",new BigDecimal("249.00"),new BigDecimal("3.50"),new BigDecimal("4.15"),new BigDecimal("871.50"),new BigDecimal("1033.35"),new BigDecimal("476.21"),new BigDecimal("2381.06"));

		orders.add(or1);
		orders.add(or2);
		orders.add(or3);
		
		assertTrue(dao.checkOrderNumber(orders, 2));
		assertTrue(dao.checkOrderNumber(orders, 1));
		
		assertFalse(dao.checkOrderNumber(orders, 5));
		assertFalse(dao.checkOrderNumber(orders, 10));
	}
	
	@Test
	void checkStateTest() {
		dao = new DaoImp();
		List<Tax> taxes = dao.importTax();
		
		String s1 = "Texas";
		String s2 = "Txeas";
		
		assertTrue(dao.isStateValid(s1, taxes));
		
		assertFalse(dao.isStateValid(s2, taxes));
	}
	
	@Test
	void checkProductTest() {

		dao = new DaoImp();
		List<Product> products = dao.importProducts();
		
		String p1 = "Wood";
		String p2 = "dirt";
		
		assertTrue(dao.isProductValid(p1, products));
		assertFalse(dao.isProductValid(p2, products));
	}
	
	@Test
	void checkOrderFormatTest() {
		service = new Service();
		String orderD = "11-05-2222";
		String testOd = "11052222";
		
		String od = service.formatOrderDate(orderD);
		assertEquals(od, testOd);
		
	}
	
	@Test
	void exportTest() {
		service = new Service();
		assertDoesNotThrow(() -> service.exportAll());
	}
	
}
