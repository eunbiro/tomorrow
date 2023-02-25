package com.tomorrow.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.MemShopMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemShopMappingService {
	private final MemShopMapRepository memShopMapRepository;
	//연관 매핑 만들기(김정환)
	public Long saveMemShopMapping(Member member, Shop shop)throws Exception {
		MemShopMapping memShopMapping = new MemShopMapping();
		memShopMapping.setMember(member);
		memShopMapping.setShop(shop);
		memShopMapping.setWorkStatus(0);
		
		memShopMapRepository.save(memShopMapping);
		
		//1번 mapShopMappingService 매개변수는  shop, member
		//2번 내용물은 39번 에서 42번 
	//MemShopMapping memShopMapping = new MemShopMapping();
	//memShopMapping.setMember(member);
	//memShopMapping.setShop(shop);
	//memShopMapping.setWorkStatus(0);
	
	//3번 일단 그렇게 해볼게용 ,, 감사합니다..,,
		
		return memShopMapping.getId();
	}
}
