package com.infy.demo.service;

import java.util.List;

import com.infy.demo.entity.Order;

public interface OrderService {
	
	Order getOrder(Integer id) throws Exception;
	
	List<Order> getCustomerOrders(Integer custId) throws Exception;
	
	List<Order> getAllOrders() throws Exception;
	
	Integer addOrder(Order order) throws Exception;
	
	Integer deleteOrder(Integer orderId) throws Exception;
	
	Integer addOrderExistingCustomer(Order order) throws Exception;
	
	Integer updatePrice(Order order, Integer orderId) throws Exception;

}
