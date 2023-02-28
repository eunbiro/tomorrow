package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.CommuteDto;
<<<<<<< HEAD
import com.tomorrow.dto.MemShopMappingDto;
=======
import com.tomorrow.dto.ManagerCommuteDto;
>>>>>>> 9c4a5bfa8905b18489ede53c59065c74993b3f28
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Commute;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.CommuteRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommuteService {
	private final CommuteRepository commuteRepository;
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	
	// 출퇴근 기록 리스트
	@Transactional(readOnly = true)
	public List<CommuteDto> getCommuteList(Long shopId, Long memberId){

		List<Commute> commuteList = commuteRepository.findByShopIdAndMemberIdOrderByIdDesc(shopId, memberId);
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
	
	public List<Commute> getCommuteListForManager(Long shopId){
		List<Commute> commuteList = commuteRepository.findByShopIdOrderByIdDesc(shopId);
		return commuteList;
	}
	
	
	
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
	
	//최근 출근기록찾기
	public CommuteDto commuteListchk(Long shopId, Long memberId) {
				
    	List<CommuteDto> commuteList = getCommuteList(shopId, memberId);
    	    			
    	if(commuteList.size() == 0 ) {
    		CommuteDto commuteDto = new CommuteDto();	
        	return commuteDto;
    		
    	} 
    	
		return commuteList.get(0);	
    	    
	}

	// 내가 가진 매장 정보를 불러오기 (select)
	/*@Transactional(readOnly = true)
		public List<MemShopMappingDto> getMyShopList(String userId) {
			Member member = findMember(userId); //관리자 정보 가져오는 메소드
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
				memShopMappingDto.setTimePay(mapping.getTimePay());
				memShopMappingDto.setPartTime(mapping.getPartTime());
				memShopMappingDto.setWorkStatus(mapping.getWorkStatus());
				
				// DTO 객체를 DTO 리스트 객체에 다시 저장
				memShopMappingDtoList.add(memShopMappingDto);
				
			}
			
			return memShopMappingDtoList;
		}*/
	
}
