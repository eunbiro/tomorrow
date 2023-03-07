package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tomorrow.dto.HireListDto;
import com.tomorrow.entity.HireList;

public interface HireListRepository extends JpaRepository<HireList, Long> {
	
	List<HireList> findByShopId(Long shopId);
}
