package com.infy.demo.integration.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;


import com.infy.demo.DemoApplication;
import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;

@SpringBootTest(classes = DemoApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class OrderIntegrationTest {
	
	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    @org.junit.jupiter.api.Order(1)
    public void getOrderTestAll() {
    	String url = "http://localhost:"+port+"/Order/getOrder/{orderId}";
    	Order order = restTemplate.getForObject(url, Order.class, 103);
    	assertTrue(order.getOrderId().equals(103));
    	assertTrue(order.getCustomer().getCustomerId().equals(501));
    	assertTrue(order.getItem().equals("Basketball"));
 	
    }
    
    @Test
    @org.junit.jupiter.api.Order(2)
    public void getCustomerOrdersTestAll() {
    	String url = "http://localhost:"+port+"/Order/getCustomerOrders/{custId}";
    	ResponseEntity<Order[]> response = restTemplate.getForEntity(url, Order[].class, 504);
    	Order[] order = response.getBody();
    	assertTrue(order.length==2);
 	
    }
    
    @Test
    @org.junit.jupiter.api.Order(3)
    public void getAllOrdersTestAll() {
    	String url = "http://localhost:"+port+"/Order/getAllOrders";
    	ResponseEntity<Order[]> response = restTemplate.getForEntity(url, Order[].class);
    	Order[] order = response.getBody();
    	assertTrue(order.length==7);
 	
    }
    
    @Test
    @org.junit.jupiter.api.Order(4)
    public void addOrderTestAll() {
    	String addUrl = "http://localhost:"+port+"Order/addOrder";
    	Order order = new Order();
		order.setItem("Smartphone");
		order.setPrice(6000);
		order.setQuantity(3);
    	Customer cust = new Customer();
		cust.setEmail("lee@gmail.com");
		cust.setName("Lee");
		order.setCustomer(cust);
    	String message = restTemplate.postForObject(addUrl, order, String.class);
    	assertTrue(message.equals("Order added successfully with ID: 108"));
    	String getUrl = "http://localhost:"+port+"/Order/getOrder/{orderId}";
    	Order order1 = restTemplate.getForObject(getUrl, Order.class, 108);
    	assertTrue(order1.getOrderId().equals(108));
    	assertTrue(order1.getCustomer().getCustomerId().equals(506));
    	assertTrue(order1.getItem().equals(order.getItem()));
  
    }
    
    @Test
    @org.junit.jupiter.api.Order(5)
    public void addOrderExistingCustomerTestAll() {
    	String addUrl = "http://localhost:"+port+"Order/addOrderCustomer";
    	Order order = new Order();
		order.setItem("Smartphone");
		order.setPrice(6000);
		order.setQuantity(3);
    	Customer cust = new Customer();
		cust.setEmail("sam@gmail.com");
		order.setCustomer(cust);
    	String message = restTemplate.postForObject(addUrl, order, String.class);
    	assertTrue(message.equals("Order added successfully with ID: 109"));
    	String getUrl = "http://localhost:"+port+"/Order/getOrder/{orderId}";
    	Order order1 = restTemplate.getForObject(getUrl, Order.class, 109);
    	assertTrue(order1.getOrderId().equals(109));
    	assertTrue(order1.getCustomer().getCustomerId().equals(502));
    	assertTrue(order1.getCustomer().getEmail().equals("sam@gmail.com"));
    	assertTrue(order1.getItem().equals(order.getItem()));
  
    }
    
    @Test
    @org.junit.jupiter.api.Order(6)
    public void deleteOrderTestAll() {
    	String delUrl = "http://localhost:" + port + "/Order/deleteOrder/{orderId}";
    	restTemplate.delete(delUrl, 105);
    	String getUrl = "http://localhost:" + port + "/Order/getOrder/{orderId}";
    	Order order = restTemplate.getForObject(getUrl, Order.class, 105);
    	assertTrue(order.getOrderId()==null);
    	
    }
    
    @Test
    @org.junit.jupiter.api.Order(7)
    public void updateCustomerEmailTestAll() {
    	String updateUrl = "http://localhost:" + port + "/Order/updatePrice/{orderId}";
    	Order order = new Order();
    	order.setPrice(350);
    	restTemplate.put(updateUrl, order, 104);
    	String getUrl = "http://localhost:"+ port+ "/Order/getOrder/{orderId}";
    	Order order1 = restTemplate.getForObject(getUrl, Order.class, 104);
    	assertTrue(order1.getOrderId().equals(104));
    	assertTrue(order1.getPrice().equals(350));
    	
    }


}
