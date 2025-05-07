package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prs.model.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Integer>{
	List<LineItem> findByRequestId(int requestId);
	@Query("SELECT SUM(li.quantity * p.price) FROM LineItem li JOIN li.product p WHERE li.request.id = :requestId AND li.product IS NOT NULL")
	Double sumTotalForRequest(@Param("requestId") int requestId);

}
