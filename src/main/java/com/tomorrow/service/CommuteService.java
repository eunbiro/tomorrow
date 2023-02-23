package com.tomorrow.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.entity.Commute;
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

		List<Commute> commuteList = commuteRepository.findByShopId(shopId);
		List<CommuteDto> commuteDtoList = new ArrayList<>();

		
		for (Commute commute : commuteList) {

			CommuteDto commuteDto = new CommuteDto();

			commuteDto.setId(commute.getId());
			commuteDto.setWorking(commute.getWorking());
			commuteDto.setLeaving(commute.getLeaving());

			commuteDtoList.add(commuteDto);
		}

		return commuteDtoList;

	}
	
	//출근 등록 insert
	public Commute saveCommute(Commute commute) {
		return commuteRepository.save(commute);
	}

	
}
