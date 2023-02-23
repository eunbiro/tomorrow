package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.ShopImg;

public interface ShopImgRepository extends JpaRepository<ShopImg, Long> {
	List<ShopImg> findByShopImgId(Long shopImgId);
}
