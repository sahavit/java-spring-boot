package com.infy.demo.dao.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import com.infy.demo.dao.CustomerRepository;
import com.infy.demo.entity.Customer;

@DataJpaTest
public class CustomerDAOTest {
	
	@Autowired    
	TestEntityManager entityManager; 
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	public void findByEmailTest() {
		Customer cust = customerRepository.findByEmail("mark@gmail.com");
		Assert.assertEquals((Integer)501,cust.getCustomerId());
		Assert.assertEquals("Mark",cust.getName());
		
	}
	
	@Test
	public void findByCustomerIdTest() {
		Customer cust = customerRepository.findByCustomerId(505);
		Assert.assertEquals("kim@gmail.com",cust.getEmail());
		Assert.assertEquals("Kim",cust.getName());
		
	}
	
	@Test
	public void addCustomerTest() {
		Customer cust = new Customer();
		cust.setEmail("lee@gmail.com");
		cust.setName("Lee");
		Customer customer = entityManager.persistAndFlush(cust);
		Assert.assertEquals(cust.getEmail(),customer.getEmail());
		Assert.assertEquals(cust.getName(),customer.getName());
		Assert.assertEquals((Integer)506,customer.getCustomerId());
		
	}

}
