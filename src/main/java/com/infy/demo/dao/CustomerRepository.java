package com.infy.demo.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.infy.demo.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
	Customer findByEmail(String email);
	
	Customer findByCustomerId(Integer id);

}
