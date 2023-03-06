package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.Hire;

public interface HireRepository extends JpaRepository<Hire, Long> {
	
	/*
	 * List<Hire> findByMemberId(Long id);
	 * 
	 * List<Hire> findByShopIdAndShopNm(Long shopId);
	 */
	

}
