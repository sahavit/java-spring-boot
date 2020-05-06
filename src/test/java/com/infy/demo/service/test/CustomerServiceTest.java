package com.infy.demo.service.test;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;

import com.infy.demo.dao.CustomerRepository;
import com.infy.demo.dao.OrderRepository;
import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;
import com.infy.demo.service.CustomerService;
import com.infy.demo.service.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceTest {
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	OrderRepository orderRepository;
	
	@InjectMocks
	CustomerService customerService = new CustomerServiceImpl();

	@Test
	public void getCustomerTest() throws Exception {
		Customer cust = new Customer();
		cust.setCustomerId(501);
		cust.setEmail("mark@gmail.com");
		cust.setName("Mark");
		Mockito.when(customerRepository.findByEmail("mark@gmail.com")).thenReturn(cust);
		Customer customer = customerService.getCustomer("mark@gmail.com");
		Assert.assertEquals(cust, customer);
		Mockito.when(customerRepository.findByEmail("abc@gmail.com")).thenReturn(null);
		assertThrows(Exception.class, () -> {customerService.getCustomer("abc@gmail.com");}, "Service.CustomerNotFound");
		
	}
	
	@Test
	public void addCustomerTest() throws Exception {
		Customer cust = new Customer();
		cust.setCustomerId(506);
		cust.setEmail("henry@gmail.com");
		cust.setName("Henry");
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		int id  = customerService.addCustomer(cust);
		Assert.assertEquals(id, 506);
		Mockito.when(customerRepository.save(cust)).thenReturn(null);
		assertThrows(Exception.class, () -> {customerService.addCustomer(cust);}, "Service.CustomerNotAdded");

		
	}

	@Test
	public void deleteCustomerTest() throws Exception {
		Customer cust = new Customer();
		cust.setCustomerId(501);
		cust.setEmail("mark@gmail.com");
		cust.setName("Mark");
		Order order = new Order();
		order.setOrderId(103);
		order.setItem("Basketball");
		order.setPrice(200);
		order.setQuantity(3);
		order.setCustomer(cust);
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		Mockito.when(customerRepository.findByCustomerId(501)).thenReturn(cust);
		Mockito.when(orderRepository.findByCustomerCustomerId(cust.getCustomerId())).thenReturn(orders);
		int id  = customerService.deleteCustomer(cust.getCustomerId());
		Assert.assertEquals(id, 501); 
		
	}
	
	@Test
	public void updateCustomerEmailTest() throws Exception {
		Customer cust = new Customer();
		cust.setCustomerId(501);
		cust.setEmail("mark@gmail.com");
		cust.setName("Mark");
		Customer newCust = new Customer();
		newCust.setEmail("mark123@gmail.com");
		Mockito.when(customerRepository.findByCustomerId(501)).thenReturn(cust);
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		int id  = customerService.updateCustomerEmail(newCust,501);
		Assert.assertEquals(id, 501);
		Mockito.when(customerRepository.findByCustomerId(510)).thenReturn(null);
		assertThrows(Exception.class, () -> {customerService.updateCustomerEmail(newCust,510);}, "Service.CustomerNotFound");
		
	}
}
