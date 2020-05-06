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

import com.infy.demo.dao.OrderRepository;
import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;
import com.infy.demo.service.OrderService;
import com.infy.demo.service.OrderServiceImpl;


@SpringBootTest
public class OrderServiceTest {
	
	@Mock
	OrderRepository orderRepository;
	
	@InjectMocks
	OrderService orderService = new OrderServiceImpl();
	
	@Test
	public void getOrderTest() throws Exception {
		Order order = new Order();
		order.setOrderId(102);
		order.setItem("Smartphone");
		Customer cust = new Customer();
		cust.setCustomerId(503);
		order.setCustomer(cust);
		Mockito.when(orderRepository.findByOrderId(102)).thenReturn(order);
		Order newOrder = orderService.getOrder(102);
		Assert.assertEquals(order, newOrder);
		Mockito.when(orderRepository.findByOrderId(1509)).thenReturn(null);
		assertThrows(Exception.class, () -> {orderService.getOrder(1509);}, "Service.OrderNotFound");
	}
	
	@Test
	public void getCustomerOrdersTest() throws Exception {
		Order order = new Order();
		order.setOrderId(102);
		order.setItem("Smartphone");
		Customer cust = new Customer();
		cust.setCustomerId(503);
		order.setCustomer(cust);
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		Mockito.when(orderRepository.findByCustomerCustomerId(503)).thenReturn(orders);
		List<Order> newOrders = orderService.getCustomerOrders(503);
		Assert.assertEquals(orders.size(), newOrders.size());
		Mockito.when(orderRepository.findByCustomerCustomerId(5035)).thenReturn(null);		
		assertThrows(Exception.class, () -> {orderService.getOrder(5035);}, "Service.OrderNotFound");
	}
	
	@Test
	public void getAllOrdersTest() throws Exception {
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
		Mockito.when(orderRepository.findAll()).thenReturn(orders);
		List<Order> newOrders = orderService.getAllOrders();
		Assert.assertEquals(orders.size(), newOrders.size());
		List<Order> emptyOrders = new ArrayList<Order>();
		Mockito.when(orderRepository.findAll()).thenReturn(emptyOrders);		
		assertThrows(Exception.class, () -> {orderService.getAllOrders();}, "Service.NoOrders");
	}
	
	@Test
	public void addOrderTest() throws Exception {
		Order order = new Order();
		order.setOrderId(102);
		order.setItem("Smartphone");
		Customer cust = new Customer();
		cust.setCustomerId(503);
		order.setCustomer(cust);
		Mockito.when(orderRepository.save(order)).thenReturn(order);
		Integer orderId = orderService.addOrder(order);
		Assert.assertEquals(order.getOrderId(),orderId);
		Mockito.when(orderRepository.save(order)).thenReturn(null);
		assertThrows(Exception.class, () -> {orderService.addOrder(order);}, "Service.OrderNotAdded");
		
	}

	@Test
	public void deleteCustomerTest() throws Exception {
		Order order = new Order();
		order.setOrderId(102);
		order.setItem("Smartphone");
		Customer cust = new Customer();
		cust.setCustomerId(503);
		order.setCustomer(cust);
		Mockito.when(orderRepository.findByOrderId(order.getOrderId())).thenReturn(order);
		Mockito.when(orderRepository.save(order)).thenReturn(order);
		orderService.deleteOrder(102);
		Mockito.when(orderRepository.findByOrderId(150)).thenReturn(null);
		assertThrows(Exception.class, () -> {orderService.deleteOrder(150);}, "Service.OrderNotFound");
		
	}
	
	@Test
	public void updateCustomerEmailTest() throws Exception {
		Order order = new Order();
		order.setOrderId(102);
		order.setItem("Smartphone");
		order.setPrice(600);
		Customer cust = new Customer();
		cust.setCustomerId(503);
		order.setCustomer(cust);
		Order order2 = new Order();
		order.setPrice(850);
		Mockito.when(orderRepository.findByOrderId(102)).thenReturn(order);
		Mockito.when(orderRepository.save(order)).thenReturn(order);
		int id  = orderService.updatePrice(order2,102);
		Assert.assertEquals(102, id);
		Mockito.when(orderRepository.findByOrderId(150)).thenReturn(null);	
		assertThrows(Exception.class, () -> {orderService.updatePrice(order2,150);}, "Service.OrderNotFound");
		
	}

}
