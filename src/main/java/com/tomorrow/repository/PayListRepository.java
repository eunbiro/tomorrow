package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tomorrow.entity.PayList;

public interface PayListRepository extends JpaRepository<PayList, Long>, QuerydslPredicateExecutor<PayList> {

	@Query(value = "select * from pay_list p where p.map_id = :mapId order by reg_time Asc", nativeQuery = true)
	List<PayList> findByMapId(@Param("mapId") Long mapId);


}
