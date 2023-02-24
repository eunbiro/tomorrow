package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.dto.ShopImgDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.entity.ShopImg;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShopImgRepository;
import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopInfoService {
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	private final ShopImgRepository shopImgRepository;
	private final MemShopMapRepository mapRepository;

	// 현재 접속해있는 관리자정보를 불러옴
	@Transactional(readOnly = true)
	public Member findMember(String userId) {

		return memberRepository.findByUserId(userId);
	}

	// 내가 가지고 있는 매장정보를 불러옴 (select 박스에 매장 목록 가져오기)
	@Transactional(readOnly = true)
	public List<MemShopMappingDto> getMyShop(String userId) {

		Member member = findMember(userId);
		List<MemShopMappingDto> myShopList = getMapping(member.getId());
		return myShopList;
	}

	// 매핑정보 DTO저장
	@Transactional(readOnly = true)
	public List<MemShopMappingDto> getMapping(Long shopId) {
		// MemShop 테이블 데이터 가져오기
		List<MemShopMapping> memShopMappingList = mapRepository.findByShopId(shopId);
		
		// 엔티티 저장할 DTO 리스트 객체 생성
		List<MemShopMappingDto> memShopMappingDtoList = new ArrayList<>();

		for (MemShopMapping mapping : memShopMappingList) {

			// DTO 객체 생성
			MemShopMappingDto memShopMappingDto = new MemShopMappingDto();

			// DTO 객체에 엔티티를 하나씩 넣어준다.
			memShopMappingDto.setShopDto(getShop(mapping.getShop()));
			memShopMappingDto.setMemberFormDto(getMember(mapping.getMember()));
			memShopMappingDto.setPartTime(mapping.getPartTime());
			memShopMappingDto.setTimePay(mapping.getTimePay());
			memShopMappingDto.setWorkStatus(mapping.getWorkStatus());

			// DTO 객체를 위에서 만들어 둔 DTO 리스트 객체에 저장
			memShopMappingDtoList.add(memShopMappingDto);
		}

		// DTO 리스트 리턴
		return memShopMappingDtoList;
	}

	// 매장 DTO
	public ShopDto getShop(Shop shop) {
		/*
		List<ShopImg> shopImgList = shopImgRepository.findByShopId(shopId);
		List<ShopImgDto> shopImgDtoList = new ArrayList<>();

		for (ShopImg shopImg : shopImgList) {
			ShopImgDto shopImgDto = ShopImgDto.of(shopImg);
			shopImgDtoList.add(shopImgDto);
		}
		*/

		ShopDto shopDto = new ShopDto();

		shopDto.setShopId(shop.getId());
		shopDto.setShopNm(shop.getShopNm());
		shopDto.setBusinessId(shop.getBusinessId());
		shopDto.setShopPlace(shop.getShopPlace());
		shopDto.setShopType(shop.getShopType());
		shopDto.setShopImgDto(getShopImg(shop.getId()));

		return shopDto;
	}

	// 매장 이미지 DTO
	public List<ShopImgDto> getShopImg(Long shopId) {
		List<ShopImg> shopImgList = shopImgRepository.findByShopId(shopId);
		List<ShopImgDto> shopImgDtoList = new ArrayList<>();
		
		for (ShopImg shopImg : shopImgList) {
			ShopImgDto shopImgDto = ShopImgDto.of(shopImg);
			shopImgDtoList.add(shopImgDto);
		}
		
		return shopImgDtoList;
	}

	// 회원 DTO
	public MemberFormDto getMember(Member member) {

		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setUserId(member.getUserId());
		memberFormDto.setUserNm(member.getUserNm());

		return memberFormDto;
	}

	// 매장찾기
	public Shop findShop(Long shopId) {
		return shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
	}

	// 매장코드로 이미지 찾기
	public List<ShopImg> findShopImg(Long shopId) {
		return shopImgRepository.findByShopId(shopId);
	}

	/*
	 * TODO 1. 매장 코드로 이미지를 찾는다. 2. 찾은 이미지 엔티티를 DTO로 변환시킨다. 3.
	 * 
	 */
}
