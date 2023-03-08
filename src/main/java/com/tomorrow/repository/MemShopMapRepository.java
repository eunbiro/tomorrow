package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.entity.MemShopMapping;

public interface MemShopMapRepository extends JpaRepository<MemShopMapping, Long> {

	List<MemShopMapping> findByMemberId(Long memberId);

	List<MemShopMapping> findByShopId(Long shopId);

	MemShopMapping findByMemberIdAndShopId(Long memberId, Long shopId);
	
	@Query("select i from MemShopMapping i where i.workStatus != '0'")
	List<MemShopMapping> findByMemberIdNotAdmin(Long memberId);
	
	@Query(value = "select * from mem_shop_mapping where work_status != 0 and shop_id = :shopId order by work_status asc", nativeQuery = true)
	List<MemShopMapping> findByShopIdNotAdmin(@Param("shopId") Long shopId);

	MemShopMapping findByMemberIdAndShopIdAndWorkStatus(Long memberId, Long shopId, int workStatus);
	
}
