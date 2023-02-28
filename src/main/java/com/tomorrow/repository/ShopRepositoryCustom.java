package com.tomorrow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tomorrow.dto.ShopCheckDto;
import com.tomorrow.entity.Shop;

public interface ShopRepositoryCustom {
	Page<Shop> getShopCheckPage(ShopCheckDto shopCheckDto, Pageable pageable);
}
