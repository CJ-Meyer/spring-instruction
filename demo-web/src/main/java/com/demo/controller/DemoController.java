package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Movie;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
	// add 2 actions, 1 post
	// get first and last names and print to the page 
	// Get price and quantity(pathVariable) print the total price to the page 
	@GetMapping("/getName/{FirstName}/{LastName}")
	public String getName(@PathVariable String FirstName,@PathVariable String LastName) {
		return "Hello, " + FirstName + " " + LastName;
	}
	@GetMapping("/getPrice/{price}/{quantity}")
	public String getPrice(@PathVariable double price, @PathVariable int quantity) {
		double totalPrice = price * quantity;
		return "Total Price: " + totalPrice;
	}
	@PostMapping("/postMovie")
	public String postMovie(@RequestBody Movie movie) {
		return movie.toString();
	}
	
}
