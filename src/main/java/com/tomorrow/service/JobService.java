package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.HireDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Hire;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.HireRepository;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShopImgRepository;
import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	private final ShopImgRepository shopImgRepository;
	private final MemShopMapRepository mapRepository;
	private final HireRepository hireRepository;
	private final ShopImgService shopImgService;

	// 현재 접속해있는 관리자정보를 불러옴
	@Transactional(readOnly = true)
	public Member findMember(String userId) {
		return memberRepository.findByUserId(userId);
	}
	
	// 모든 공고 가져오기 test
		@Transactional(readOnly = true)
		public Hire findHire() {
			return (Hire) hireRepository.findAll();
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
	public List<MemShopMappingDto> getMapping(Long memberId) {

		List<MemShopMapping> memShopMappingList = mapRepository.findByMemberId(memberId);
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

	// 회원 DTO
	public MemberFormDto getMember(Member member) {

		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setUserId(member.getUserId());
		memberFormDto.setUserNm(member.getUserNm());

		return memberFormDto;
	}

	// 매장 정보
	@Transactional(readOnly = true)
	public ShopDto getShop(Shop shop) {
		ShopDto shopDto = new ShopDto();

		shopDto.setShopNm(shop.getShopNm());
		shopDto.setShopPlace(shop.getShopPlace());
		shopDto.setShopType(shop.getShopType());
		shopDto.setShopId(shop.getId());

		return shopDto;
	}

	// 구인공고 등록
	public Hire saveHire(Hire hire) throws Exception {

		return hireRepository.save(hire);
	}
	
	    // 공고 DTO 저장
		@Transactional(readOnly = true)
		public List<HireDto> getJobView() {

			List<Hire> hireList = hireRepository.findAll();
			List<HireDto> hireDtoList = new ArrayList<>();

			for (Hire hire : hireList) {

				HireDto hireDto = new HireDto();
				
				hireDto.setShopDto(getShop(hire.getShop()));
				hireDto.setMemberFormDto(getMember(hire.getMember()));
				hireDto.setHirePeriod(hire.getHirePeriod());
				hireDto.setHireTime(hire.getHireTime());
				hireDto.setHirePay(hire.getHirePay());
				hireDto.setHireNum(hire.getHireNum());
				hireDto.setId(hire.getId());
				
				
				hireDtoList.add(hireDto);
			}

			return hireDtoList;
		}
		
		// 구인공고 리스트
		@Transactional(readOnly = true)
      	public List<HireDto> getHireList() {
			List<HireDto> hireList = getJobView();
			return hireList;		
		}
		
	
	

	// 매장찾기
	public Shop findShop(Long shopId) {
		return shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
	}




}
