package com.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.model.Movie;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
	@GetMapping("/")
	public String sayHello() {
		return "Hello, World!";
	}
	@GetMapping("/{nbr}")
	public String sayHello(@PathVariable int nbr) {
		return "Hello, World! "+nbr;
	}
	@PostMapping("/")
	public String addMovie(@RequestBody Movie movie) {
		return movie.toString();
	}
	@GetMapping("/get-name-age/{name}/{age}")
	public String getNameAndAge(@PathVariable String name,@PathVariable int age) {
		return "Hello, " + name + " who is " + age+ " years old";
	}
	
	@GetMapping("/stuff")
	public String getStuff(String var1 , String var2, String var3) {
		return "Stuff: " + var1 + ", " + var2 + ", " + var3;
	}
	
	
}
