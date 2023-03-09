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
			//shop을 만들어
			Shop shop = createShopFormDto.createShop();
			//shop을 레퍼지토리에 저장해
			shopRepository.save(shop);
			
			//이미지를 저장해
			for (int i = 0; i < createShopImgFileList.size(); i++) {
				ShopImg shopImg = new ShopImg();
				shopImg.setShop(shop);
				
				
				shopImgService.saveShopImg(shopImg, createShopImgFileList.get(i));
			}
			Member member = shopService.findMember(userId); //유저 id; 
			Shop findShop = shopService.findShop(shop.getId());
			
			//매장 생성하면서 매장이랑 멤버랑 이어주는 걸 만들어버림
			MemShopMapping memberShopMapping = MemShopMapping.createAdminMapping(findShop, member);
			memShopMapRepository.save(memberShopMapping);
			
			return shop.getId(); 
		}
		public void deleteMapId(Long memberId) {
			
			List<MemShopMapping> memShopMappingList = memShopMapRepository.findByMemberId(memberId);
			
			for (MemShopMapping memShopMapping : memShopMappingList) {
				
				memShopMapRepository.delete(memShopMapping);
			}
		}
		
}
