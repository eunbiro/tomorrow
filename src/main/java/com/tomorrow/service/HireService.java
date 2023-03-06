package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tomorrow.dto.HireDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.dto.WorkLogDto;
import com.tomorrow.entity.Hire;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.entity.WorkLog;
import com.tomorrow.repository.HireRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HireService {

	private final HireRepository hireRepository;
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	
	// 구인공고 내용을 가져옴
	@Transactional
	public List<HireDto> getHire(String userId) {
		
		Member member = findMember (userId);
		List<HireDto> hireList = getHireList(member.getId());
		return hireList;
	}

	// 현재 접속해있는 관리자정보를 불러옴
	@Transactional
	public Member findMember(String userId) {

		return memberRepository.findByUserId(userId);
	}
	
	// 공고 DTO 저장
	@Transactional
	public List<HireDto> getHireList(Long memberId) {

		List<Hire> hireList = hireRepository.findByMemberId(memberId);
		List<HireDto> hireDtoList = new ArrayList<>();

		for (Hire hire : hireList) {

			HireDto hireDto = new HireDto();
			
			hireDto.setShopDto(getShop(hire.getShop()));
			hireDto.setMemberFormDto(getMember(hire.getMember()));
			hireDto.setHirePay(hire.getHirePay());
			hireDto.setId(hire.getId());
			
			
			hireDtoList.add(hireDto);
		}

		return hireDtoList;
	}
	
	// shop 정보 DTO 저장
	public ShopDto getShop(Shop shop) {

		ShopDto shopDto = new ShopDto();

		if (shop != null) {
			
			shopDto.setShopId(shop.getId());
			shopDto.setShopNm(shop.getShopNm());
			shopDto.setShopPlace(shop.getShopPlace());
		}
		
		return shopDto;
	}
	
	// 회원 정보 DTO저장
		public MemberFormDto getMember(Member member) {

			MemberFormDto memberFormDto = new MemberFormDto();
			memberFormDto.setUserId(member.getUserId());
			memberFormDto.setUserNm(member.getUserNm());

			return memberFormDto;
		}

		public void deleteHire(Long hireId) {
			
			Hire hire = hireRepository.findById(hireId).orElseThrow(EntityNotFoundException::new);
			hireRepository.delete(hire);
			
		}
}
