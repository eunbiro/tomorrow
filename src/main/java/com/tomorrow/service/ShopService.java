package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.NoticeDto;
import com.tomorrow.dto.NoticeLikeDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
import com.tomorrow.entity.NoticeLike;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.NoticeLikeRepository;
import com.tomorrow.repository.ShopRepository;
import com.tomorrow.repository.noticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional 
@RequiredArgsConstructor
public class ShopService {

	private final noticeRepository noticeRepository;
	private final NoticeLikeRepository noticeLikeRepository;
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	private final MemShopMapRepository mapRepository;
	
	// 매장공지 내용을 가져옴
	@Transactional(readOnly = true)
	public List<NoticeDto> getNoticeList(String shopId) {
		
		/* TODO 1. 관리자 아이디로 매장레파지토리 조회해서 매장 코드 얻기
		 * 		2. 관리자 코드와 매장코드로 공지레파지토리 조회
		 * 		3. 가져와야할 데이터 프로필, 관리자 이름, 관리자 아이디, 공지내용, 좋아요, 작성일
		*/
		
		List<Notice> noticList = noticeRepository.findByShopIdOrderByIdDesc(Long.parseLong(shopId));
		List<NoticeDto> noticeDtoList = new ArrayList<>();

		for (Notice notice : noticList) {
			
			NoticeDto noticeDto  = new NoticeDto();
			
			noticeDto.setNoticeId(notice.getId());
			noticeDto.setMemberFormDto(getMember(notice.getMember()));
			noticeDto.setNoticeCont(notice.getNoticeCont());
			noticeDto.setNoticeLikeDto(getNotiLike(notice.getId()));
			noticeDto.setRegTime(notice.getRegTime());
			
			noticeDtoList.add(noticeDto);
		}
		
		return noticeDtoList;
	}
	
	// 공지번호로 좋아요 갯수 가져옴
	public List<NoticeLikeDto> getNotiLike(Long noticeId) {
		
		List<NoticeLike> noticeLikeList = noticeLikeRepository.findByNoticeId(noticeId)
														  .orElseThrow(EntityNotFoundException::new);
		List<NoticeLikeDto> noticeLikeDtoList = new ArrayList<>();
		
		for (NoticeLike noticeLike : noticeLikeList) {
			
			NoticeLikeDto noticeLikeDto = new NoticeLikeDto();
			
			noticeLikeDto.setNotiLikeId(noticeLike.getId());
			noticeLikeDto.setMemberFormDto(getMember(noticeLike.getMember()));
			noticeLikeDto.setLikeCount(noticeLike.getLikeCount());
		}
		return noticeLikeDtoList;
	}
	
	// 내가 가지고 있는 매장정보를 불러옴
	public List<MemShopMappingDto> getMyShop(String userId) {
		
		Member member = findMember(userId);
		List<MemShopMappingDto> myShopList = getMapping(member.getId());
		return myShopList;
	}
	
	// 현재 접속해있는 관리자정보를 불러옴 
	public Member findMember(String userId) {
		
		return memberRepository.findByUserId(userId);
	}
	
	// 회원 정보 DTO저장
	public MemberFormDto getMember(Member member) {
		
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setUserId(member.getUserId());
		memberFormDto.setUserNm(member.getUserNm());
		//memberFormDto.setUserProfile(member.getUserProfile());
		
		return memberFormDto;
	}
	
	// shop 정보 DTO 저장
	public ShopDto getShop(Shop shop) {
		
		ShopDto shopDto = new ShopDto();
		
		shopDto.setShopId(shop.getId());
		shopDto.setShopNm(shop.getShopNm());
		
		return shopDto;
	}
	
	// 매핑정보 DTO저장
	public List<MemShopMappingDto> getMapping(Long memberId) {
		
		List<MemShopMapping> memShopMappingList = mapRepository.findByMemberId(memberId);
		List<MemShopMappingDto> memShopMappingDtoList = new ArrayList<>();
		
		for (MemShopMapping mapping : memShopMappingList) {
			
			MemShopMappingDto memShopMappingDto = new MemShopMappingDto();
			
			
			memShopMappingDto.setShopDto(getShop(mapping.getShop()));
			memShopMappingDto.setMemberFormDto(getMember(mapping.getMember()));
			memShopMappingDto.setPartTime(mapping.getPartTime());
			memShopMappingDto.setTimePay(mapping.getTimePay());
			memShopMappingDto.setWorkStatus(mapping.getWorkStatus());
			
			memShopMappingDtoList.add(memShopMappingDto);
		}
		
		return memShopMappingDtoList;
	}
	
	// 매장공지 내용을 insert
	public Notice saveNotice(Notice notice) {
		
		return noticeRepository.save(notice);
	}
	
	public Shop findShop(Long ShopId) {
		
		return shopRepository.findById(ShopId).orElseThrow(EntityNotFoundException::new);
	}
	
}
