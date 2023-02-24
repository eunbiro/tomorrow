package com.tomorrow.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityNotFoundException;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.entity.Commute;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.CommuteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommuteService {
	private final CommuteRepository commuteRepository;

	// 출퇴근 기록 리스트
	@Transactional(readOnly = true)
	public List<CommuteDto> getCommuteList(Long shopId){

		List<Commute> commuteList = commuteRepository.findByShopIdOrderByIdDesc(shopId);
		List<CommuteDto> commuteDtoList = new ArrayList<>();

		
		for (Commute commute : commuteList) {

			CommuteDto commuteDto = new CommuteDto();

			commuteDto.setId(commute.getId());
			commuteDto.setWorking(commute.getWorking());
			commuteDto.setWorking(commute.getWorking());
			commuteDto.setLeaving(commute.getLeaving());

			commuteDtoList.add(commuteDto);
		}

		return commuteDtoList;

	}
	
	//TODO : List<Commute> commuteList = commuteRepository.findByShopIdOrderByIdDesc(shopId); .get(0) 인덱스 번호 0번짜리 찾아서 등록
	
	//출근 등록 insert
	public Commute saveCommute(Commute commute) throws Exception{
	
		return commuteRepository.save(commute);
	}
	
	//출퇴근 기록 찾기
	@Transactional(readOnly = true)
	public Commute findByCommuteId(Long id) {
		return commuteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	
	//퇴근 등록 update
	public void updateCommute(Long id, CommuteDto commuteDto, Member member, Shop shop) {
	
		
		Commute commute = commuteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		commute.updateCommute(commuteDto, member, shop);
	}



	
}
