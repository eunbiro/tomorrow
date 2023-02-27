package com.tomorrow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.entity.ShopImg;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopCreateService {
	private final MemShopMapRepository memShopMapRepository;
	private final ShopService shopService;
	private final ShopRepository shopRepository;
	private final ShopImgService shopImgService;
	// 매장 만들기 (김정환)
		public Long saveShop(CreateShopFormDto createShopFormDto, List<MultipartFile> createShopImgFileList, String userId) throws Exception {
			Shop shop = createShopFormDto.createShop();
			shopRepository.save(shop);

			for (int i = 0; i < createShopImgFileList.size(); i++) {
				ShopImg shopImg = new ShopImg();
				shopImg.setShop(shop);
				
				shopImgService.saveShopImg(shopImg, createShopImgFileList.get(i));
			}
			Member member = shopService.findMember(userId); //유저 id; 
			Shop findShop = shopService.findShop(shop.getId());
			
			//create
			MemShopMapping memberShopMapping = MemShopMapping.createAdminMapping(findShop, member);
			memShopMapRepository.save(memberShopMapping);
			
			return shop.getId(); 
		}
		
}
