package com.infy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.demo.dao.CustomerRepository;
import com.infy.demo.dao.OrderRepository;
import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;

@Service(value="customerService")
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public Customer getCustomer(String email) throws Exception {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findByEmail(email);
		if(customer==null) {
			throw new Exception("Service.CustomerNotFound");
		}
		return customer;
	}

	@Override
	public Integer addCustomer(Customer customer) throws Exception {
		// TODO Auto-generated method stub
		Customer cust = customerRepository.save(customer);
		if(cust==null) {
			throw new Exception("Service.CustomerNotAdded");
		}
		return cust.getCustomerId();

	}

	@Override
	public Integer deleteCustomer(Integer customerId) throws Exception {
		// TODO Auto-generated method stub
		Customer cust = customerRepository.findByCustomerId(customerId);
		if(cust==null) {
			throw new Exception("Service.CustomerNotFound");
		}
		List<Order> orders = orderRepository.findByCustomerCustomerId(cust.getCustomerId());
		if(orders.isEmpty()) {
			throw new Exception("Service.CustomerNotFound");
		}
		for (Order order : orders) {
			order.setCustomer(null);
		}
		customerRepository.deleteById(cust.getCustomerId());
		return customerId;

	}

	@Override
	public Integer updateCustomerEmail(Customer cust, Integer custId) throws Exception {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findByCustomerId(custId);
		if(customer==null) {
			throw new Exception("Service.CustomerNotFound");
		}
		customer.setEmail(cust.getEmail());
		Customer updatedCust = customerRepository.save(customer);
		return updatedCust.getCustomerId();
	}

}
