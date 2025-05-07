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

import jakarta.transaction.Transactional;

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
					HttpStatus.NOT_FOUND, "LineItem NOT FOUND FOR id "+id);
		}
	}
	@PostMapping("")
	public LineItem add(@RequestBody LineItem LI) {
		Request request = requestRepo.findById(LI.getRequest()
				.getId()).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND, "Request NOT FOUND FOR id " + LI.getRequest().getId()));
		
				Product product = productRepo.findById(LI.getProduct().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product NOT FOUND FOR id " + LI.getProduct().getId()));
				
				LI.setProduct(product);
				LI.setRequest(request);
				lineItemRepo.save(LI);
				updateRequestTotal(request.getId());
				return LI;
		
		
	}
		
	
	@PutMapping("/{id}")
	public void putVendor(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id DOES NOT MATCH URL.");
		}
		else if (lineItemRepo.existsById(lineItem.getId())) {
			lineItemRepo.save(lineItem);
			updateRequestTotal(lineItem.getRequest().getId());
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem NOT FOUND FOR id "+id);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		if (lineItemRepo.existsById(id)) {
			Optional<LineItem> r = lineItemRepo.findById(id);
			int requestId = r.get().getRequest().getId();
			lineItemRepo.deleteById(id);
			updateRequestTotal(requestId);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem NOT FOUND FOR id "+id);
		}
	}
	
	@GetMapping("/lines-for-req/{id}")
	public List<LineItem> getByRequestId(@PathVariable int id) {
		System.out.println("In LIC getByRequestId, id: "+id);
		return lineItemRepo.findByRequestId(id); 
	}
	@Transactional
	private void updateRequestTotal(int requestId) {
	    System.out.println("Updating total for request ID: " + requestId);

	    Optional<Request> r = requestRepo.findById(requestId);

	    if (r.isPresent()) {
	        Request request = r.get();
	        Double total = lineItemRepo.sumTotalForRequest(requestId);
	        request.setTotal(total != null ? total : 0.0);
	        requestRepo.save(request);
	        System.out.println("Updated total for request ID: " + requestId + " in the database.");
	    } else {
	        System.out.println("Request ID " + requestId + " not found. No update performed.");
	    }
	}


}