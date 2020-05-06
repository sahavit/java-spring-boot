package com.infy.demo.service;

import com.infy.demo.entity.Customer;

public interface CustomerService {
	
	Customer getCustomer(String email) throws Exception;
	
	Integer addCustomer(Customer customer) throws Exception;
	
	Integer deleteCustomer(Integer custId) throws Exception;
	
	Integer updateCustomerEmail(Customer cust, Integer custId) throws Exception;

}
