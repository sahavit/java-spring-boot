package com.infy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.demo.dao.CustomerRepository;
import com.infy.demo.dao.OrderRepository;
import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;

@Service(value="orderService")
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customerRepository; 

	@Override
	public Order getOrder(Integer id) throws Exception{
		// TODO Auto-generated method stub
		Order order = orderRepository.findByOrderId(id);
		if(order==null) {
			throw new Exception("Service.OrderNotFound");
		}
		return order;
	}

	@Override
	public List<Order> getCustomerOrders(Integer custId) throws Exception {
		// TODO Auto-generated method stub
		List<Order> orders = orderRepository.findByCustomerCustomerId(custId);
		if(orders.isEmpty()) {
			throw new Exception("Service.CustomerNotFound");
		}
		return orders;
	}

	@Override
	public List<Order> getAllOrders() throws Exception {
		// TODO Auto-generated method stub
		List<Order> orders = orderRepository.findAll();
		if(orders.isEmpty()) {
			throw new Exception("Service.NoOrders");
		}
		return orders;
	}

	@Override
	public Integer addOrder(Order order) throws Exception {
		// TODO Auto-generated method stub
		Order newOrder = orderRepository.save(order);
		if(newOrder==null) {
			throw new Exception("Service.OrderNotAdded");
		}
		return newOrder.getOrderId();
	}


	@Override
	public Integer deleteOrder(Integer orderId) throws Exception {
		// TODO Auto-generated method stub
		Order order = orderRepository.findByOrderId(orderId);
		if(order==null)
			throw new Exception("Service.OrderNotFound");
		order.setCustomer(null);
		orderRepository.deleteById(orderId);
		return orderId;
	}

	@Override
	public Integer addOrderExistingCustomer(Order order) throws Exception {
		// TODO Auto-generated method stub
		Customer customer;
		if(order.getCustomer().getEmail()!=null) {
			customer  = customerRepository.findByEmail(order.getCustomer().getEmail());
		}
		else {
			customer  = customerRepository.findByCustomerId(order.getCustomer().getCustomerId());
		}
		if(customer==null) {
			throw new Exception("Service.CustomerNotFound");
		}
		order.setCustomer(customer);
		Order order1 = orderRepository.save(order);
		return order1.getOrderId();
		
	}

	@Override
	public Integer updatePrice(Order order, Integer orderId) throws Exception {
		// TODO Auto-generated method stub
		Order o = orderRepository.findByOrderId(orderId);
		if(o==null) {
			throw new Exception("Service.OrderNotFound");
		}
		o.setPrice(order.getPrice());
		Order order1 = orderRepository.save(o);
		return order1.getOrderId();
	}
	

}
