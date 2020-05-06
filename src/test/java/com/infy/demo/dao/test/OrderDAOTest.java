package com.infy.demo.dao.test;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.infy.demo.dao.OrderRepository;
import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;

@DataJpaTest
public class OrderDAOTest {

	@Autowired    
	TestEntityManager entityManager; 
	
	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void findAllTest() {
		List<Order> orders = orderRepository.findAll();
		Assert.assertEquals(7,orders.size());
	}
	
	@Test
	public void findByOrderIdTest() {
		Order order = orderRepository.findByOrderId(105);
		Assert.assertEquals((Integer)504,order.getCustomer().getCustomerId());
		Assert.assertEquals("Smartphone",order.getItem());
		Assert.assertEquals((Integer)5000,order.getPrice());
		Assert.assertEquals((Integer)1,order.getQuantity());
		
	}
	
	@Test
	public void findByCustomerCustomerIdTest() {
		List<Order> order1 = orderRepository.findByCustomerCustomerId(502);
		Assert.assertEquals(2,order1.size());
		List<Order> order2 = orderRepository.findByCustomerCustomerId(501);
		Assert.assertEquals(1,order2.size());
	}
	
	@Test
	public void addOrderTest() {
		Order order = new Order();
		order.setItem("Shirt");
		order.setPrice(560);
		order.setQuantity(8);
		Customer cust = new Customer();
		cust.setName("Joe");
		order.setCustomer(cust);
		Order newOrder = entityManager.persistAndFlush(order);
		Assert.assertEquals(order.getItem(),newOrder.getItem());
		Assert.assertEquals(order.getPrice(),newOrder.getPrice());
		Assert.assertEquals(order.getCustomer(),newOrder.getCustomer());
		Assert.assertEquals(order.getQuantity(),newOrder.getQuantity());
		Assert.assertEquals((Integer)108,newOrder.getOrderId());
		
	}
}
