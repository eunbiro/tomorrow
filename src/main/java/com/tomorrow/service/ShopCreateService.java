package com.tomorrow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.entity.Shop;
import com.tomorrow.entity.ShopImg;

import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopCreateService {
	private final ShopRepository shopRepository;
	private final ShopImgService shopImgService;
	// 매장 만들기 (김정환)
		public Long saveShop(CreateShopFormDto createShopFormDto, List<MultipartFile> createShopImgFileList)
				throws Exception {
			Shop shop = createShopFormDto.createShop();
			shopRepository.save(shop);

			for (int i = 0; i < createShopImgFileList.size(); i++) {
				ShopImg shopImg = new ShopImg();
				shopImg.setShop(shop);
				
				shopImgService.saveShopImg(shopImg, createShopImgFileList.get(i));
			}
			return shop.getId();
		}

}
