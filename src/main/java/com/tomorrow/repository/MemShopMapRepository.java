package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.MemShopMapping;

public interface MemShopMapRepository extends JpaRepository<MemShopMapping, Long> {
	
	List<MemShopMapping> findByMemberId(Long memberId);
	
	List<MemShopMapping> findByShopId(Long shopId);
}
