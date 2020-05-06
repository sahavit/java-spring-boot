package com.infy.demo.dao;

import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import com.infy.demo.entity.Customer;
import com.infy.demo.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
	
	Order findByOrderId(Integer id);

	List<Order> findByCustomerCustomerId(Integer custId);
	
	List<Order> findAll();
	
	
}
