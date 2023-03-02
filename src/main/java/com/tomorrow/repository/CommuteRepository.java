package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Commute;


public interface CommuteRepository extends JpaRepository<Commute, Long>, QuerydslPredicateExecutor<Commute>{
	
	List<Commute> findByShopIdOrderByIdDesc(Long shopId);

	Optional<Commute> findByIdOrderByIdDesc(Long id);
	
	List<Commute> findByMemberId(Long memberId);
	
	/* List<Commute> findByUserIdOrderByIdDesc(String userId); */
	//Commute findByShopIdAndMemberIdOrderByIdDesc(Long ShopId, Long CommuteId);
	
}
