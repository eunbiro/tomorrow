package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.dto.ShopImgDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.entity.ShopImg;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShopImgRepository;
import com.tomorrow.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopInfoService {
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	private final ShopImgRepository shopImgRepository;
	private final MemShopMapRepository mapRepository;
	private final ShopImgService shopImgService;
	
	/* TODO 
	 * 1. 폼태그 수정 (사용 X) ✔✔
	 * 2. 매장 정보 불러올 때 사진 클릭하면 원본 사진 팝업으로 띄우기 (새 창 X)
	 * 3. 매장 정보 불러올 때 map API 띄우기 
	 * */
	
	// 현재 접속해있는 관리자정보를 불러옴
	@Transactional(readOnly = true)
	public Member findMember(String userId) {

		return memberRepository.findByUserId(userId);
	}

	// 내가 가지고 있는 매장정보를 불러옴 (select 박스에 매장 목록 가져오기)
	@Transactional(readOnly = true)
	public List<MemShopMappingDto> getMyShop(String userId) {
		Member member = findMember(userId);
		List<MemShopMappingDto> myShopList = getMapping(member.getId());
		return myShopList;
	}

	// 매장 DTO (매장 이미지 DTO 정보까지 넣어주기) 
	@Transactional(readOnly = true)
	public ShopDto getShop(Shop shop) {
		ShopDto shopDto = new ShopDto();

		shopDto.setShopId(shop.getId());
		shopDto.setShopNm(shop.getShopNm());
		shopDto.setBusinessId(shop.getBusinessId());
		shopDto.setShopPlace(shop.getShopPlace());
		shopDto.setShopType(shop.getShopType());
		shopDto.setShopImgDto(getShopImg(shop.getId()));

		return shopDto;
	}

	// 매장 이미지 DTO
	public List<ShopImgDto> getShopImg(Long shopId) {
		List<ShopImg> shopImgList = shopImgRepository.findByShopId(shopId);
		List<ShopImgDto> shopImgDtoList = new ArrayList<>();

		for (ShopImg shopImg : shopImgList) {
			ShopImgDto shopImgDto = ShopImgDto.of(shopImg);
			shopImgDtoList.add(shopImgDto);
		}

		return shopImgDtoList;
	}

	// 회원 DTO
	public MemberFormDto getMember(Member member) {

		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setUserId(member.getUserId());
		memberFormDto.setUserNm(member.getUserNm());

		return memberFormDto;
	}

	// 매장찾기
	public Shop findShop(Long shopId) {
		return shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
	}

	// 매장코드로 이미지 찾기
	public List<ShopImg> findShopImg(Long shopId) {
		return shopImgRepository.findByShopId(shopId);
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
	
	// 매장 정보 가져오기
	@Transactional(readOnly = true)
	public CreateShopFormDto getShopInfoDtl(Long shopId) {
		List<ShopImg> shopImgList = shopImgRepository.findByShopId(shopId);
		List<ShopImgDto> shopImgDtoList = new ArrayList<>();
		
		for (ShopImg shopImg : shopImgList) {
			ShopImgDto shopImgDto = ShopImgDto.of(shopImg);
			shopImgDtoList.add(shopImgDto);
		}
		
		Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
		
		CreateShopFormDto createShopFormDto = CreateShopFormDto.of(shop);
		createShopFormDto.setCreateShopImgDtoList(shopImgDtoList);
		
		return createShopFormDto;
		
	}
	
	// 매장 정보 수정 
	public Long updateShopInfo(CreateShopFormDto createShopFormDto, List<MultipartFile> shopImgFileList) throws Exception {
		Shop shop = shopRepository.findById(createShopFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		shop.updateShopInfo(createShopFormDto);
		
		List<Long> shopImgIds = createShopFormDto.getCreateShopImgIds(); // 매장 이미지 아이디 리스트 조회
		
		for(int i=0; i<shopImgFileList.size(); i++) {
			shopImgService.updateShopImg(shopImgIds.get(i), shopImgFileList.get(i));
		}
		return shop.getId();
	}

}
