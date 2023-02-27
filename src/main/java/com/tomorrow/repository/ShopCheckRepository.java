package com.tomorrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Shop;

public interface ShopCheckRepository extends JpaRepository<Shop, Long>, 
	QuerydslPredicateExecutor<Shop>,ShopRepositoryCustom{
	
}
