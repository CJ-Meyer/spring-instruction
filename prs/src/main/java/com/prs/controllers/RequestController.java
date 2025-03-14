package com.prs.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.prs.db.RequestRepo;
import com.prs.db.UserRepo;
import com.prs.model.Request;
import com.prs.model.RequestCreateDTO;
import com.prs.model.User;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
		@Autowired RequestRepo requestRepo;
		@Autowired UserRepo userRepo;

		@GetMapping("/")
		public List<Request> getAll() {
	        return requestRepo.findAll();
	    }
		
		@GetMapping("/{id}")
		public Optional<Request> getById(@PathVariable int id) {
			Optional<Request> r = requestRepo.findById(id);
			if (r.isPresent()) {
				return r;
			}
			else {
				throw new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Request not found for id "+id);
			}
		}
		@PostMapping("")
		public Request add(@RequestBody RequestCreateDTO rc) {
			Request r = new Request();
			Optional<User> u = userRepo.findById(rc.getUserId());
			r.setStatus("NEW");
			r.setSubmittedDate(LocalDateTime.now());
			r.setTotal(0.0);
			r.setRequestNumber(generateRequestNumber());
			r.setDateNeeded(rc.getDateNeeded());
			r.setDescription(rc.getDescription());
			r.setJustification(rc.getJustification());
			r.setDeliveryMode(rc.getDeliveryMode());
			r.setUser(u.get());
			return requestRepo.save(r);
		}
		@PutMapping("/{id}")
		public void update(@PathVariable int id, @RequestBody Request request) {
			if (id != request.getId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request id mismatch vs URL.");
			}
			else if (requestRepo.existsById(request.getId())) {
				requestRepo.save(request);
			}
			else {
				throw new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Request not found for id "+id);
			}
		}
		
		@DeleteMapping("/{id}")
		public void delete(@PathVariable int id) {
			if (requestRepo.existsById(id)) {
				requestRepo.deleteById(id);
			}
			else {
				throw new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Request not found for id "+id);
			}
		}
		private String generateRequestNumber() {
	        // Get today's date as YYYYMMDD
	        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

	        // Find the latest request that starts with today's date
	        Optional<Request> lastRequest = requestRepo.findTopByRequestNumberStartingWithOrderByRequestNumberDesc(datePart);

	        int newSequence = 1; // Default start number
	        if (lastRequest.isPresent()) {
	            String lastRequestNumber = String.valueOf(lastRequest.get().getRequestNumber());
	            int lastSequence = Integer.parseInt(lastRequestNumber.substring(8)); // Extract last 4 digits
	            newSequence = lastSequence + 1;
	        }

	        // Format new sequence as a 4-digit number
	        String sequencePart = String.format("%04d", newSequence);

	        return datePart + sequencePart;
	    }
}
