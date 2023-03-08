package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.dto.HireDto;
import com.tomorrow.entity.Hire;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HireRepository extends JpaRepository<Hire, Long>, QuerydslPredicateExecutor<Hire> {
	
	List<Hire> findByMemberId(Long memberId);

	List<Hire> findByShopId(Long shopId);
	
    Optional<Hire> findById(Long id);
    
   // List<HireDto> getHireList(HireDto hireDto);
    
}
