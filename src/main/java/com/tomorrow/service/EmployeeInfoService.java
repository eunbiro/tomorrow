package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeInfoService {
	/* TODO
	 * 1. DELETE 버튼 ✔
	 * 2. workStatus랑 ROLE 처리  
	 * 3. 시급 0으로 뜨는거 고칠 수 있나 확인해보기 
	 * 4. input에 원래 있는 값 불러오지 못하는 거 해결  
	 * 5. 엑셀 다운로드
	 * 6. CSS 수정 
	 */
	private final MemberRepository memberRepository;
	private final MemShopMapRepository mapRepository;

	// 현재 접속 중인 관리자 정보 가져오기
	@Transactional(readOnly = true)
	public Member findMember(String userId) {
		return memberRepository.findByUserId(userId);
	}
	
	public Member findEmplMember(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
	}

	// 내가 가진 매장 정보를 불러오기 (select)
	@Transactional(readOnly = true)
	public List<MemShopMappingDto> getMyShopList(String userId) {
		Member member = findMember(userId); // 관리자 정보 가져오는 메소드
		List<MemShopMappingDto> myShopList = getMapping(member.getId());
		return myShopList;
	}

	// 매핑 정보 엔티티 -> DTO 변환해서 정보 가져오기
	public List<MemShopMappingDto> getMapping(Long memberId) {
		List<MemShopMapping> memShopMappingList = mapRepository.findByMemberId(memberId); // 멤버아이디를 통해 매핑엔티티에서 정보를 뽑아오기
		List<MemShopMappingDto> memShopMappingDtoList = new ArrayList<>(); // 매핑디티오 객체(정보를 담을 껍데기) 생성

		for (MemShopMapping mapping : memShopMappingList) {
			// 매핑 DTO 객체 생성
			MemShopMappingDto memShopMappingDto = new MemShopMappingDto();

			// DTO객체에 엔티티를 하나씩 넣어준다
			memShopMappingDto.setMemberFormDto(getMember(mapping.getMember()));
			memShopMappingDto.setShopDto(getShop(mapping.getShop()));
			/*
			 * memShopMappingDto.setTimePay(mapping.getTimePay());
			 * memShopMappingDto.setPartTime(mapping.getPartTime());
			 * memShopMappingDto.setWorkStatus(mapping.getWorkStatus());
			 */

			// DTO 객체를 DTO 리스트 객체에 다시 저장
			memShopMappingDtoList.add(memShopMappingDto);
		}

		return memShopMappingDtoList;
	}

	// 매장 DTO 가져오기 (매핑 DTO에 넣어줄 데이터)
	public ShopDto getShop(Shop shop) {
		ShopDto shopDto = new ShopDto();

		shopDto.setShopId(shop.getId());
		shopDto.setShopNm(shop.getShopNm());

		return shopDto;
	}

	// 회원 DTO 가져오기 (매핑 DTO에 넣어줄 데이터)
	public MemberFormDto getMember(Member member) {
		MemberFormDto memberFormDto = new MemberFormDto();

		memberFormDto.setUserId(member.getUserId());
		memberFormDto.setUserNm(member.getUserNm());
		memberFormDto.setPNum(member.getPNum());
		memberFormDto.setRole(member.getRole());

		return memberFormDto;
	}

	// shopId로 매핑DTO 정보 가져오기 
	public List<MemShopMappingDto> getMappingList(Long shopId) {
		List<MemShopMapping> memShopMappingList = mapRepository.findByShopIdNotAdmin(shopId);
		List<MemShopMappingDto> memShopMappingDtoList = new ArrayList<>();

		for (MemShopMapping mapping : memShopMappingList) {
			MemShopMappingDto memShopMappingDto = new MemShopMappingDto();

			// DTO객체에 엔티티를 하나씩 넣어준다
			memShopMappingDto.setMemberFormDto(getMember(mapping.getMember()));
			memShopMappingDto.setShopDto(getShop(mapping.getShop()));
			memShopMappingDto.setTimePay(mapping.getTimePay());
			memShopMappingDto.setPartTime(mapping.getPartTime());
			memShopMappingDto.setWorkStatus(mapping.getWorkStatus());
			memShopMappingDto.setMapId(mapping.getId());
			
			memShopMappingDtoList.add(memShopMappingDto);
		}
		
		return memShopMappingDtoList;
	}
	
	// 매장 직원 정보 내용 update
	public void updateEmplInfo(Long mapId, MemShopMappingDto updateMappingDto, Member member, Shop shop) {
		MemShopMapping memShopMapping = findMapping(mapId);
		memShopMapping.updateEmplInfo(updateMappingDto, member, shop);
		//Member member = memberRepository.findById(memShopMapping.getMember().getId()).orElseThrow(EntityNotFoundException::new);
		
		member.updateRole(Role.ALBA);
	}

	public MemShopMapping findMapping(Long mapId) {

		return mapRepository.findById(mapId).orElseThrow(EntityNotFoundException::new);
	}
	
	// 직원 정보 내용 delete 
	public void deleteEmployee(MemShopMapping memShopMapping) {
		
		mapRepository.delete(memShopMapping);
	}

}
