package com.prs.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer>{
	Optional<Request> findTopByRequestNumberStartingWithOrderByRequestNumberDesc(String datePrefix);
}
