package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tomorrow.dto.HireDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.WorkLogDto;
import com.tomorrow.entity.Hire;
import com.tomorrow.entity.WorkLog;
import com.tomorrow.repository.HireRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HireService {
	
	 private final HireRepository hireRepository; private final MemberRepository
	 memberRepository; private final ShopRepository shopRepository;
	 
	 // 구인공고 내용을 가져옴
	  
	 @Transactional(readOnly = true) public List<HireDto> getHireList(Long hireId, List<MemShopMappingDto> memShopMappingDto) {
	 
	 List<Hire> hireList = hireRepository.findByShopIdOrderByIdDesc(shopId);
	 List<WorkLogDto> workLogDtoList = new ArrayList<>();
	 
	 for (Hire workLog : hireList) {
	 
	 WorkLogDto workLogDto = new WorkLogDto();
	 
	 workLogDto.setWorkLogId(workLog.getId());
	 workLogDto.setMemberFormDto(getMember(workLog.getMember()));
	 workLogDto.setLogCont(workLog.getLogCont());
	 workLogDto.setRegTime(workLog.getRegTime());
	 workLogDto.setUpdateTime(workLog.getUpDateTime());
	 workLogDto.setPartTime(getPartTime(workLog.getMember(), workLog.getShop()));
	 
	 workLogDtoList.add(workLogDto); }
	 
	 return workLogDtoList;
	 }
}
