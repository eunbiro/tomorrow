package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.ManagerDto;
import com.tomorrow.dto.NoticeDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Manager;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.ManagerRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShopRepository;
import com.tomorrow.repository.noticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional 
@RequiredArgsConstructor
public class ShopService {

	private final noticeRepository noticeRepository;
	private final ManagerRepository managerRepository;
	private final ShopRepository shopRepository;
	
	// 매장공지 내용을 가져옴
	@Transactional(readOnly = true)
	public List<NoticeDto> getNoticeList(String userId) {
		
		/* TODO 1. 관리자 아이디로 매장레파지토리 조회해서 매장 코드 얻기
		 * 		2. 관리자 코드와 매장코드로 공지레파지토리 조회
		 * 		3. 가져와야할 데이터 프로필, 관리자 이름, 관리자 아이디, 공지내용, 좋아요, 작성일
		*/
		
		ManagerDto managerDto = getManager(managerRepository.findByUserId(userId));
		List<ShopDto> shopDtoList = getShop(managerDto.getManagerId());
		
		List<NoticeDto> noticeDtoList = new ArrayList<>();


		
		return noticeDtoList;
	}
	
	public Manager findManager(String userId) {
		
		return managerRepository.findByUserId(userId);
	}
	
	
	// 관리자 정보 DTO저장
	public ManagerDto getManager(Manager manager) {
		
		ManagerDto managerDto = new ManagerDto();
		managerDto.setUserId(manager.getUserId());
		managerDto.setUserNm(manager.getUserNm());
		managerDto.setUserProfile(manager.getUserProfile());
		
		return managerDto;
	}
	
	public List<ShopDto> getShop(Long managerId) {
		
		List<Shop> shopList = shopRepository.findByManagerId(managerId);
		List<ShopDto> shopDtoList = new ArrayList<>();
		
		for (Shop shop : shopList) {
			
			ShopDto shopDto = new ShopDto();
			
			shopDto.setShopId(shop.getId());
			shopDto.setShopNm(shop.getShopNm());
			shopDto.setManager(shop.getManager());
			
			shopDtoList.add(shopDto);
		}
		
		return shopDtoList;
	}
	
	// 매장공지 내용을 insert
	public Notice saveNotice(Notice notice) {
		
		return noticeRepository.save(notice);
	}
	
	
	
}
