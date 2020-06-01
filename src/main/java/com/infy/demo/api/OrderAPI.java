package com.infy.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.demo.entity.Order;
import com.infy.demo.service.OrderService;

@CrossOrigin
@RestController
@RequestMapping("Order")
public class OrderAPI {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/getOrder/{orderId}")
	ResponseEntity<Order> getOrder(@PathVariable Integer orderId) throws Exception{
		
		try {
			
			Order order = orderService.getOrder(orderId);
			
			return ResponseEntity.ok(order);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()));
		}
	}
	
	@GetMapping("/getCustomerOrders/{custId}")
	ResponseEntity<List<Order>> getCustomerOrders(@PathVariable Integer custId) throws Exception{
		
		try {
			
			List<Order> orders = orderService.getCustomerOrders(custId);
			
			return ResponseEntity.ok(orders);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()));
		}
	}
	
//	@GetMapping("/getAllOrders")
//	ResponseEntity<List<Order>> getAllOrders() throws Exception {
//		
//		try {
//			
//			List<Order> orders = orderService.getAllOrders();
//			
//			return ResponseEntity.ok(orders);
//		}
//		catch(Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty(e.getMessage()));
//		}
//	}
	
	@PostMapping("/addOrder")
	ResponseEntity<String> addOrder(@RequestBody Order order) throws Exception{
		
		try {
			
			Integer orderId = orderService.addOrder(order);
			
			String message = "Order added successfully with ID: "+orderId;
			
			return ResponseEntity.ok(message);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty(e.getMessage()));
		}
	}
	
	@DeleteMapping("/deleteOrder/{orderId}")
	ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) throws Exception{
		
		try {
			
			Integer id = orderService.deleteOrder(orderId);
			
			String message = "Order deleted successfully with ID: "+id;
			
			return ResponseEntity.ok(message);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty(e.getMessage()));
		}
	}
	
	@PostMapping("/addOrderCustomer")
	ResponseEntity<String> addOrderExistingCustomer(@RequestBody Order order) throws Exception{
		
		try {
			
			Integer orderId = orderService.addOrderExistingCustomer(order);
			
			String message = "Order added successfully with ID: "+orderId;
			
			return ResponseEntity.ok(message);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}

	@PutMapping("/updatePrice/{orderId}")
	ResponseEntity<String> updatePrice (@PathVariable Integer orderId, @RequestBody Order order) throws Exception{
		
		try {
			
			Integer id = orderService.updatePrice(order, orderId);
			
			String message = "Order updated successfully with ID: "+id;
			
			return ResponseEntity.ok(message);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
	
	

}
