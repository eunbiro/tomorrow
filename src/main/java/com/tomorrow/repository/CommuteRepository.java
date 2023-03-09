package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tomorrow.entity.Commute;


public interface CommuteRepository extends JpaRepository<Commute, Long>, QuerydslPredicateExecutor<Commute>{
	
	List<Commute> findByShopIdOrderByIdDesc(Long shopId);

	Optional<Commute> findByIdOrderByIdDesc(Long id);
	
	List<Commute> findByMemberId(Long memberId);
	
	List<Commute> findByShopId(Long shopId);
	
	/* List<Commute> findByUserIdOrderByIdDesc(String userId); */
	//Commute findByShopIdAndMemberIdOrderByIdDesc(Long ShopId, Long CommuteId);
	
	@Query(value = "select * from commute where member_id = :memberId and shop_id = :shopId order by working desc", nativeQuery = true)
	public List<Commute> findCommuteByMemberId(@Param("memberId") Long memberId, @Param("shopId") Long shopId);

	List<Commute> findByMemberIdOrderByIdDesc(Long memberId);
}
