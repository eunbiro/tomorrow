package com.tomorrow.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Commute;


public interface CommuteRepository extends JpaRepository<Commute, Long>, QuerydslPredicateExecutor<Commute>{
	
	List<Commute> findByShopId(Long id);

	Optional<Commute> findById(Long id);
}
