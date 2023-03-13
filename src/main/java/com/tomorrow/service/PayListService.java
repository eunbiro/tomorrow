package com.tomorrow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.PayListDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Commute;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.PayList;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.CommuteRepository;
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
	private final CommuteRepository commuteRepository;
	private final MemShopMapRepository mapRepository;
	
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
	public PayList savePayList(Long commteId, Member member, Shop shop) {
		
		MemShopMapping memShopMapping = memShopMapRepository.findByMemberIdAndShopId(member.getId(), shop.getId());
		
		Commute commute = commuteRepository.findById(commteId).orElseThrow(EntityNotFoundException::new);
		
		LocalDateTime working = commute.getWorking();
		LocalDateTime leaving = commute.getLeaving();
		
		long workTime = ChronoUnit.HOURS.between(working, leaving);
		int timePay = memShopMapping.getTimePay();
		int dayPay = (int)workTime * timePay;
		
		PayList payList = PayList.createPayList(dayPay, memShopMapping);
		
		return payListRepository.save(payList);
	}
	
	// 접속한 회원 정보로 mapping 불러오기
	@Transactional(readOnly = true)
	public List<MemShopMapping> getMappingList(String userId) {
		
		return memShopMapRepository.findByMemberId(findMember(userId).getId());
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
	
	// 매장별로 나눠서 리스트 넣어주기
	@Transactional(readOnly = true)
	public List<List<PayListDto>> getMapShopList(String userId) {
		
		List<MemShopMapping> memShopMappingList = getMappingList(userId);
		List<List<PayListDto>> payListDtoList = new ArrayList<>();
		
		for (MemShopMapping memShopMapping : memShopMappingList) {
			
			List<PayList> payLists = payListRepository.findByMapId(memShopMapping.getId());
			
			if (payLists.size() != 0) {
				
				List<PayListDto> payListDtos = new ArrayList<>();
				
				for (PayList payList : payLists) {
					
					PayListDto payListDto = new PayListDto();
					
					payListDto.setDayPay(payList.getDayPay());
					payListDto.setShopDto(getShopDto(payList.getMemShopMapping().getShop()));
					payListDto.setRegTime(payList.getRegTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
					payListDto.setMemShopMappingDto(getMSDto(payList.getMemShopMapping()));
					
					payListDtos.add(payListDto);
				}
				
				payListDtoList.add(payListDtos);
			}
		}
		
		return payListDtoList;
	}
	
	// memShop DTO로 변환
	public MemShopMappingDto getMSDto (MemShopMapping memShopMapping) {
		
		MemShopMappingDto memShopMappingDto = new MemShopMappingDto();
		memShopMappingDto.setWorkStatus(memShopMapping.getWorkStatus());
		
		return memShopMappingDto;
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
	
	@Transactional(readOnly = true)
	public int getWorkDays(MemShopMapping msm) {
		List<Commute> commuteListByMember = commuteRepository.findCommuteByMemberId(msm.getMember().getId(), msm.getShop().getId());
		int workDays = commuteListByMember.size();
		
		return workDays;
	}
	
	@Transactional(readOnly = true)
	public List<PayListDto> getPayListByMonth(Long shopId, int month){
		List<PayListDto> payListDto = new ArrayList<PayListDto>();
		List<PayList> payList = payListRepository.findPayListByMonth(shopId, month);		
		
		for(PayList pl : payList) {
			PayListDto pld = new PayListDto();
			pld.setMemShopMappingDto(getMapById(pl.getMemShopMapping().getId()));
			pld.setId(pl.getId());
			pld.setWorkDays(payList.size());
			pld.setRegTime(pl.getRegTime().toString());
			pld.setMonthPay(pl.getDayPay() * payList.size());
			pld.setShopDto(ShopDto.of(pl.getMemShopMapping().getShop()));
			payListDto.add(pld);
		}
	
		return payListDto;
	}
	
	@Transactional(readOnly = true)
	public MemShopMappingDto getMapById(Long mapId) {
		MemShopMapping msm = memShopMapRepository.findById(mapId).orElseThrow(EntityNotFoundException::new);
		MemShopMappingDto msmd = new MemShopMappingDto();
		msmd.setMapId(msm.getId());
		msmd.setMemberFormDto(MemberFormDto.of(msm.getMember()));
		msmd.setTimePay(msm.getTimePay());
		msmd.setPartTime(msm.getPartTime());
	
		return msmd;
	}
}
