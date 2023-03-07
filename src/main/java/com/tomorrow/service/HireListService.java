package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tomorrow.dto.HireDto;
import com.tomorrow.dto.HireListDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Hire;
import com.tomorrow.entity.HireList;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.HireListRepository;
import com.tomorrow.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HireListService {
	private final HireListRepository hireListRepository;

	public List<HireListDto> getHireList(Long shopId) {

		List<HireList> hireList = hireListRepository.findByShopId(shopId);
		List<HireListDto> hireListDtos = new ArrayList<>();

		for (HireList hl : hireList) {
			
			HireListDto hireListDto = new HireListDto();

			hireListDto.setId(hl.getId());
			hireListDto.setMemberFormDto(MemberFormDto.of(hl.getMember()));
			hireListDto.setShopDto(ShopDto.of(hl.getShop()));
			
			hireListDtos.add(hireListDto);
		}

		return hireListDtos;
	}
}
