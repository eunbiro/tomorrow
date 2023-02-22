package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tomorrow.entity.Commute;


public interface CommuteRepository extends JpaRepository<Commute, Long>{
	
	List<Commute> findByShopId(Long id);
}
