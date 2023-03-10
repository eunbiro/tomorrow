package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>,QuerydslPredicateExecutor<Shop>, ShopRepositoryCustom {
	
	List<Shop> findByShopNm(String shopNm);
	
	List<Shop> findByShopNmOrBusinessId(String shopNm, Long BusinessId);
}