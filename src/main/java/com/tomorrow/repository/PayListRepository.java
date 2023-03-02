package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.PayList;

public interface PayListRepository extends JpaRepository<PayList, Long>, QuerydslPredicateExecutor<PayList> {

//	List<PayList> findByMapId(Long mapId);

}
