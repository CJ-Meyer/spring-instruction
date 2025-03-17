package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer>{
	Optional<Request> findTopByRequestNumberStartingWithOrderByRequestNumberDesc(String datePrefix);
	Optional<Request> findById(int id);
	List<Request> findAllByStatusAndUserIdNot(String string, int userId);
}
