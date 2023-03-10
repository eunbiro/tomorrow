package com.tomorrow.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopCheckDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopCheckService {
	private final MemShopMapRepository mapRepository;
	private final MemberRepository memberRepository;
	
	//현재 접속해있는 아이디 정보를 불러온다.
	@Transactional(readOnly = true)
	public Member findMember(String userId) {

		return memberRepository.findByUserId(userId);
	}
	
	
	//내가 가지고 있는 매장 정보를 불러온다.
	@Transactional(readOnly=true)
	public List<MemShopMappingDto> getMemShop(String userId){
		//받은 유저 아이디로 멤버를 찾는다.
		Member member = findMember(userId);
		
		//member.getId();
		List<MemShopMappingDto> memShopMappingDtoList = getMapping(member.getId());
		return memShopMappingDtoList;
	}
	
	//매핑정보 DTO 저장
	@Transactional(readOnly = true)
	public List<MemShopMappingDto> getMapping(Long memberId) {
		
		//memShopRepository 에 저장된 것 중 memberId를 갖는 걸 가져와서 memShopMapping 리스트에 넣어준다.
		List<MemShopMapping> memShopMappingList = mapRepository.findByMemberId(memberId);
		
		//MemShopMappingDto 로 된 리스트를 만들어준다.
		List<MemShopMappingDto> memShopMappingDtoList = new ArrayList<>();
		
		//아까 만든 리스트를 MemShopMapping 엔티티에 하나씩 넣어준다.
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
	
	//shop 만들어버리기~
	@Transactional(readOnly = true)
	public ShopDto getShop(Shop shop) {
		ShopDto shopDto = new ShopDto();

		shopDto.setShopId(shop.getId());
		shopDto.setShopNm(shop.getShopNm());
		shopDto.setBusinessId(shop.getBusinessId());
		shopDto.setShopPlace(shop.getShopPlace());
		shopDto.setShopType(shop.getShopType());
		

		return shopDto;
	}
	//멤버 만들어버리기
	public MemberFormDto getMember(Member member) {

		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setUserId(member.getUserId());
		memberFormDto.setUserNm(member.getUserNm());

		return memberFormDto;
	}
	
}
