package com.tomorrow.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.ShopCheckDto;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.ShopCheckRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopCheckService {
	private final ShopCheckRepository shopCheckRepository;
	
	@Transactional(readOnly = true)
	public Page<Shop> getCheckShopPage(ShopCheckDto shopSearchDto, Pageable pageable){
		return shopCheckRepository.getShopCheckPage(shopSearchDto, pageable);
	}
}
