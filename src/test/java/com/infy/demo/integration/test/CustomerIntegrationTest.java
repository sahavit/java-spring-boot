package com.infy.demo.integration.test;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


import com.infy.demo.DemoApplication;
import com.infy.demo.entity.Customer;

@SpringBootTest(classes = DemoApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {
	
	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void getCustomerTestAll() {
    	String url = "http://localhost:"+port+"/Customer/getCustomer/{email}";
    	Customer customer = restTemplate.getForObject(url, Customer.class, "mark@gmail.com");
    	assertTrue(customer.getCustomerId().equals(501));
 	
    }
    
    @Test
    public void addCustomerTestAll() {
    	String addUrl = "http://localhost:"+port+"/Customer/addCustomer";
    	Customer cust = new Customer();
		cust.setEmail("lee@gmail.com");
		cust.setName("Lee");
    	String message = restTemplate.postForObject(addUrl, cust, String.class);
    	String getUrl = "http://localhost:"+port+"/Customer/getCustomer/{email}";
    	Customer customer = restTemplate.getForObject(getUrl, Customer.class, "lee@gmail.com");
    	assertTrue(message!=null);
    	assertTrue(customer.getEmail().equals("lee@gmail.com"));
    	assertTrue(customer.getName().equals("Lee"));
  
    }
    
    @Test
    public void deleteCustomerTestAll() {
    	String delUrl = "http://localhost:" + port + "/Customer/deleteCustomer/{customerId}";
    	restTemplate.delete(delUrl, 503);
    	String getUrl = "http://localhost:" + port + "/Customer/getCustomer/{email}";
    	Customer cust = restTemplate.getForObject(getUrl, Customer.class,"joe@gmail.com");
    	assertTrue(cust.getCustomerId()==null);
    	
    }
    
    @Test
    public void updateCustomerEmailTestAll() {
    	String updateUrl = "http://localhost:" + port + "/Customer/updateEmail/{custId}";
    	Customer customer = new Customer();
    	customer.setEmail("sam123@gmail.com");
    	restTemplate.put(updateUrl, customer, 502);
    	String getUrl = "http://localhost:" + port + "/Customer/getCustomer/{email}";
    	Customer cust = restTemplate.getForObject(getUrl, Customer.class,"sam123@gmail.com");
    	assertTrue(cust.getCustomerId().equals(502));
    	assertTrue(cust.getEmail().equals(customer.getEmail()));
    	
    }

}
