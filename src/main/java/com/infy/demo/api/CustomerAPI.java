package com.infy.demo.api;

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

import com.infy.demo.entity.Customer;
import com.infy.demo.service.CustomerService;

@CrossOrigin
@RestController
@RequestMapping("Customer")
public class CustomerAPI {
	
	@Autowired
	private CustomerService customerService;
		
	@Autowired
	private Environment environment;
	
	@GetMapping("/getCustomer/{email:.+}")
	ResponseEntity<Customer> getCustomer(@PathVariable String email) throws Exception{
		
		try {
			
			Customer customer = customerService.getCustomer(email);
			
			return ResponseEntity.ok(customer);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
	}
	
	@PostMapping("/addCustomer")
	ResponseEntity<String> addCustomer(@RequestBody Customer customer) throws Exception{
		try {
			Integer custId = customerService.addCustomer(customer);
			
			String message = "Customer added successfully with ID: "+custId;
			
			return ResponseEntity.ok(message);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty(e.getMessage()));
		}
		
	}
	
	@DeleteMapping("/deleteCustomer/{customerId}")
	ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws Exception{
		try {
			Integer custId = customerService.deleteCustomer(customerId);

			String message = "Customer deleted successfully with ID: "+custId;
			
			return ResponseEntity.ok(message);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty(e.getMessage()));
		}
		
	}
	
	@PutMapping("/updateEmail/{custId}")
	ResponseEntity<String> updateCustomerEmail(@RequestBody Customer customer, @PathVariable Integer custId) throws Exception{
		try {
			
			Integer id = customerService.updateCustomerEmail(customer,custId);
			
			String message = "Customer updated successfully with ID: "+id;
			
			return ResponseEntity.ok(message);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty(e.getMessage()));
		}
		
	}
}
