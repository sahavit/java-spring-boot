package com.infy.demo.api.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.infy.demo.service.CustomerService;

@SpringBootTest(classes = DemoApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerAPITest {

	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Mock
    private CustomerService customerService;
    
    @Test
    public void getCustomerTest() throws Exception {
    	String url = "http://localhost:"+port+"/Customer/getCustomer/{email}";
    	Customer cust = new Customer();
    	cust.setCustomerId(501);
    	cust.setEmail("mark@gmail.com");
    	cust.setName("Mark");
    	Mockito.when(customerService.getCustomer("mark@gmail.com")).thenReturn(cust);
    	Customer customer = restTemplate.getForObject(url, Customer.class, "mark@gmail.com");
    	assertTrue(customer.getCustomerId().equals(501));
 	
    }
    
    @Test
    public void addCustomerTest() throws Exception {
    	String addUrl = "http://localhost:"+port+"/Customer/addCustomer";
    	Customer cust = new Customer();
    	cust.setCustomerId(506);
		cust.setEmail("lee@gmail.com");
		cust.setName("Lee");
		Mockito.when(customerService.addCustomer(cust)).thenReturn(506);
    	String message = restTemplate.postForObject(addUrl, cust, String.class);
    	assertTrue(message.equals("Customer added successfully with ID: 506"));
  
    }
    
    @Test
    public void deleteCustomerTest() throws Exception {
    	String url = "http://localhost:" + port + "/Customer/deleteCustomer/{customerId}";
    	Mockito.when(customerService.deleteCustomer(504)).thenReturn(504);
    	String requestBody = "{\"status\":\"testStatus2\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class, 504);
    	String actual = response.getBody();
    	String expected = "Customer deleted successfully with ID: 504";
    	assertTrue(actual.equals(expected));
    	
    }
    
    @Test
    public void updateCustomerEmailTest() throws Exception {
    	String url = "http://localhost:" + port + "/Customer/updateEmail/{custId}";
    	Customer customer = new Customer();
    	customer.setEmail("sam123@gmail.com");
    	customer.setCustomerId(502);
    	customer.setName("Sam");
    	Mockito.when(customerService.updateCustomerEmail(customer,502)).thenReturn(502);
    	String requestBody = "{\"status\":\"testStatus2\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, 502);
    	String actual = response.getBody();
    	String expected = "Customer updated successfully with ID: 502";
    	assertTrue(actual.equals(expected));
    	assertTrue(customer.getCustomerId().equals(502));
    	assertTrue(customer.getEmail().equals(customer.getEmail()));
    	
    }

}
