package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.dto.HireDto;
import com.tomorrow.dto.HireSearchDto;
import com.tomorrow.entity.Hire;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HireRepository extends JpaRepository<Hire, Long>, QuerydslPredicateExecutor<Hire> {
	
	List<Hire> findByMemberId(Long memberId);

	List<Hire> findByShopId(Long shopId);
	
    Optional<Hire> findByIdOrderByIdDesc(Long id);


    
     //List<HireDto> getHireList(HireSearchDto hireSearchDto);
    
}
