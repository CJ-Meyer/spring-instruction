package com.prs.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.prs.db.LineItemRepo;
import com.prs.db.ProductRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Product;
import com.prs.model.Request;
import com.prs.model.User;

@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")
public class LineItemController {
	@Autowired LineItemRepo lineItemRepo;
	@Autowired RequestRepo requestRepo;
	@Autowired ProductRepo productRepo;

	@GetMapping("/")
	public List<LineItem> getAll() {
        return lineItemRepo.findAll();
    }
	
	@GetMapping("/{id}")
	public Optional<LineItem> getById(@PathVariable int id) {
		Optional<LineItem> li = lineItemRepo.findById(id);
		if (li.isPresent()) {
			return li;
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Vendor not found for id "+id);
		}
	}
	@PostMapping("")
	public LineItem add(@RequestBody LineItem LI, Product product, Request request) {
		Optional<Product> p = productRepo.findById(product.getId());
		Optional<Request> r = requestRepo.findById(request.getId());
		LI.setProduct(p.get());
		LI.setRequest(r.get());
		return lineItemRepo.save(LI);
	}
	
	@PutMapping("/{id}")
	public void putVendor(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
		}
		else if (lineItemRepo.existsById(lineItem.getId())) {
			lineItemRepo.save(lineItem);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		if (lineItemRepo.existsById(id)) {
			lineItemRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
		}
	}
}