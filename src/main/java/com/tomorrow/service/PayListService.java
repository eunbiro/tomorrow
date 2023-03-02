package com.tomorrow.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.PayListDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.PayList;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.PayListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PayListService {
	
	private final MemberRepository memberRepository;
	private final MemShopMapRepository memShopMapRepository;
	private final PayListRepository payListRepository;
	
	// 현재 접속해있는 회원정보를 불러옴
	@Transactional(readOnly = true)
	public Member findMember(String userId) {
		
		return memberRepository.findByUserId(userId);
	}
	
	// 회원 정보 DTO저장
	public MemberFormDto getMember(Member member) {

		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setUserId(member.getUserId());
		memberFormDto.setUserNm(member.getUserNm());

		return memberFormDto;
	}
	
	// 퇴근 update 시 일급 insert
	public PayList savePayList(CommuteDto commuteDto, Member member, Shop shop) {
		
		MemShopMapping memShopMapping = memShopMapRepository.findByMemberIdAndShopId(member.getId(), shop.getId());
		
		LocalDateTime working = commuteDto.getWorking();
		LocalDateTime leaving = commuteDto.getLeaving();
		
		long workTime = ChronoUnit.HOURS.between(leaving, working);
		
		int timePay = memShopMapping.getTimePay();
		
		int dayPay = (int)workTime * timePay;
		
		PayList payList = PayList.createPayList(dayPay, memShopMapping);
		
		return payListRepository.save(payList);
	}
	
	// 접속한 회원 정보로 mapping 불러오기
	public List<MemShopMapping> getMappingList(String userId) {
		
		return memShopMapRepository.findByMemberId(findMember(userId).getId());
	}
	
	// mapping id로 정보가져오기
	public PayList getPayList (Long MapId) {
		
		return payListRepository.findByMapId(MapId).orElseThrow(EntityNotFoundException::new);
	}
	
	// shop 정보 DTO 저장
	public ShopDto getShopDto(Shop shop) {

		ShopDto shopDto = new ShopDto();

		if (shop != null) {
			
			shopDto.setShopId(shop.getId());
			shopDto.setShopNm(shop.getShopNm());
			shopDto.setShopPlace(shop.getShopPlace());
		}
		
		return shopDto;
	}
	
	// 접속 회원 정보로 급여리스트 불러오기
	public List<PayListDto> getPayList (String userId) {
		
		List<MemShopMapping> memShopMappingList = memShopMapRepository.findByMemberId(findMember(userId).getId());
		List<PayListDto> payListDtoList = new ArrayList<>();
		
		for (MemShopMapping memShopMapping : memShopMappingList) {
			
			PayListDto payListDto = new PayListDto();
			
			payListDto.setShopDto(getShopDto(memShopMapping.getShop()));
			payListDto.setDayPay(getPayList(memShopMapping.getId()).getDayPay());
			
			payListDtoList.add(payListDto);
		}
		
		return payListDtoList;
	}
	
	// 매장별 근무일 수 구하기
	public List<PayListDto> getWorkDay(List<PayListDto> payListDtoList) {
		
		List<PayListDto> workDayCountList = new ArrayList<>();
		
		for (PayListDto payListDto : payListDtoList) {
			
			if (workDayCountList.size() == 0) {
				
				
			}
		}
		// Collections.frequency(리스트, 특정데이터)
		
		return workDayCountList;
	}
}
