package com.infy.demo.api.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.infy.demo.DemoApplication;

import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;
import com.infy.demo.service.OrderService;

@SpringBootTest(classes = DemoApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderAPITest {
	
	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Mock
    private OrderService orderService;

    
//    @Test
//    public void getOrderTest() throws Exception {
//    	String url = "http://localhost:"+port+"/Order/getOrder/{orderId}";
//    	Order order = new Order();
//    	order.setItem("Basketball");
//    	order.setOrderId(103);
//    	Customer cust = new Customer();
//    	cust.setCustomerId(501);
//    	order.setCustomer(cust);
//    	Mockito.when(orderService.getOrder(103)).thenReturn(order);
//    	Order newOrder = restTemplate.getForObject(url, Order.class, 103);
//    	assertTrue(newOrder.getOrderId().equals(103));
//    	assertTrue(newOrder.getCustomer().getCustomerId().equals(501));
//    	assertTrue(newOrder.getItem().equals("Basketball"));
// 	
//    }
    
    @Test
    public void getCustomerOrdersTest() throws Exception {
    	String url = "http://localhost:"+port+"/Order/getCustomerOrders/{custId}";
    	Order order1 = new Order();
		Order order2 = new Order();
		List<Order> orders = new ArrayList<>();
		orders.add(order1);
		orders.add(order2);
    	Mockito.when(orderService.getCustomerOrders(502)).thenReturn(orders);
    	ResponseEntity<Order[]> response = restTemplate.getForEntity(url, Order[].class, 502);
    	Order[] order = response.getBody();
    	assertTrue(order.length==2);
 	
    }
    
    @Test
    public void getAllOrdersTest() throws Exception {
    	String url = "http://localhost:"+port+"/Order/getAllOrders";
    	Order order1 = new Order();
		Order order2 = new Order();
		Order order3 = new Order();
		Order order4 = new Order();
		Order order5 = new Order();
		Order order6 = new Order();
		Order order7 = new Order();
		List<Order> orders = new ArrayList<Order>();
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		orders.add(order4);
		orders.add(order5);
		orders.add(order6);
		orders.add(order7);
    	Mockito.when(orderService.getAllOrders()).thenReturn(orders);
    	ResponseEntity<Order[]> response = restTemplate.getForEntity(url, Order[].class);
    	Order[] order = response.getBody();
    	assertTrue(order.length==7);
 	
    }
    
    @Test
    public void addOrderTest() throws Exception {
    	String addUrl = "http://localhost:"+port+"Order/addOrder";
    	Order order = new Order();
    	order.setOrderId(108);
		order.setItem("Smartphone");
		order.setPrice(6000);
		order.setQuantity(3);
    	Customer cust = new Customer();
		cust.setEmail("lee@gmail.com");
		cust.setName("Lee");
		cust.setCustomerId(506);
		order.setCustomer(cust);
		Mockito.when(orderService.addOrder(order)).thenReturn(order.getOrderId());
    	String message = restTemplate.postForObject(addUrl, order, String.class);
    	assertTrue(message.equals("Order added successfully with ID: 108"));
  
    }
    
    @Test
    public void addOrderExistingCustomerTest() throws Exception {
    	String addUrl = "http://localhost:"+port+"Order/addOrderCustomer";
    	Order order = new Order();
		order.setItem("Smartphone");
		order.setPrice(6000);
		order.setQuantity(3);
		order.setOrderId(108);
    	Customer cust = new Customer();
		cust.setEmail("joe@gmail.com");
		order.setCustomer(cust);
		cust.setCustomerId(503);
		Mockito.when(orderService.addOrderExistingCustomer(order)).thenReturn(order.getOrderId());
    	String message = restTemplate.postForObject(addUrl, order, String.class);
    	assertTrue(message.equals("Order added successfully with ID: 108"));
    	
    }
    
    @Test
    public void deleteOrderTest() throws Exception {
    	String url = "http://localhost:" + port + "/Order/deleteOrder/{orderId}";
    	String requestBody = "{\"status\":\"testStatus2\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
        Mockito.when(orderService.deleteOrder(105)).thenReturn(105);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class, 105);
    	String actual = response.getBody();
    	String expected = "Order deleted successfully with ID: 105";
    	assertTrue(actual.equals(expected));

    }
    
    @Test
    public void updateCustomerEmailTest() throws Exception {
    	String url = "http://localhost:" + port + "/Order/updatePrice/{orderId}";
    	Order order = new Order();
    	order.setPrice(350);
    	String requestBody = "{\"status\":\"testStatus2\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
        Mockito.when(orderService.updatePrice(order, 104)).thenReturn(104);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, 104);
    	String actual = response.getBody();
    	String expected = "Order updated successfully with ID: 104";
    	assertTrue(actual.equals(expected));
    	
    }

}
