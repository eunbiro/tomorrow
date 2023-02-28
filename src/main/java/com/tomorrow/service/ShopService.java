package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.NoticeDto;
import com.tomorrow.dto.NoticeLikeDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.dto.WorkLogDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
import com.tomorrow.entity.NoticeLike;
import com.tomorrow.entity.Shop;
import com.tomorrow.entity.WorkLog;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.NoticeLikeRepository;
import com.tomorrow.repository.ShopRepository;
import com.tomorrow.repository.WorkLogRepository;
import com.tomorrow.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

	private final NoticeRepository noticeRepository;
	private final NoticeLikeRepository noticeLikeRepository;
	private final WorkLogRepository workLogRepository;
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	private final MemShopMapRepository mapRepository;

	// 매장공지 내용을 가져옴
	@Transactional(readOnly = true)
	public List<NoticeDto> getNoticeList(String shopId) {

		List<Notice> noticList = noticeRepository.findByShopIdOrderByIdDesc(Long.parseLong(shopId));
		List<NoticeDto> noticeDtoList = new ArrayList<>();

		for (Notice notice : noticList) {

			NoticeDto noticeDto = new NoticeDto();

			noticeDto.setNoticeId(notice.getId());
			noticeDto.setMemberFormDto(getMember(notice.getMember()));
			noticeDto.setNoticeCont(notice.getNoticeCont());
			noticeDto.setNotiLike(getNotiLikeList(notice.getId()));
			noticeDto.setNoticeLikeDto(getNoticeLikeDto(notice.getId(), notice.getMember().getId()));
			noticeDto.setRegTime(notice.getRegTime());
			noticeDto.setUpdateTime(notice.getUpDateTime());

			noticeDtoList.add(noticeDto);
		}

		return noticeDtoList;
	}

	// 공지번호로 좋아요 갯수 가져옴
	@Transactional(readOnly = true)
	public int getNotiLikeList(Long noticeId) {

		List<NoticeLike> noticeLikeList = noticeLikeRepository.findByNoticeId(noticeId).orElse(null);

		return noticeLikeList.size();
	}

	// 공지번호랑 회원번호로 좋아요 정보 가져옴
	public NoticeLikeDto getNoticeLikeDto(Long noticeId, Long memberId) {

		NoticeLike noticeLike = noticeLikeRepository.findByNoticeIdAndMemberId(noticeId, memberId).orElse(null);

		NoticeLikeDto noticeLikeDto = new NoticeLikeDto();

		if (noticeLike != null) {

			noticeLikeDto.setNotiLikeId(noticeLike.getId());
		}
		return noticeLikeDto;
	}

	// 내가 가지고 있는 매장정보를 불러옴
	@Transactional(readOnly = true)
	public List<MemShopMappingDto> getMyShop(String userId) {

		Member member = findMember(userId);
		List<MemShopMappingDto> myShopList = getMapping(member.getId());
		return myShopList;
	}

	// 현재 접속해있는 관리자정보를 불러옴
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

	// shop 정보 DTO 저장
	public ShopDto getShop(Shop shop) {

		ShopDto shopDto = new ShopDto();

		if (shop != null) {
			
			shopDto.setShopId(shop.getId());
			shopDto.setShopNm(shop.getShopNm());
			shopDto.setShopPlace(shop.getShopPlace());
		}
		
		return shopDto;
	}

	// 매핑정보 DTO저장
	@Transactional(readOnly = true)
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

	
	// 공지정보 DTO 저장
	public NoticeDto getNoticeDto(Notice notice) {
		
		NoticeDto noticeDto = new NoticeDto();
		
		noticeDto.setMemberFormDto(getMember(notice.getMember()));
		noticeDto.setShopDto(getShop(notice.getShop()));
		noticeDto.setNoticeCont(notice.getNoticeCont());
		noticeDto.setNoticeId(notice.getId());
		return noticeDto;
	}

	// 매장찾기
	@Transactional(readOnly = true)
	public Shop findShop(Long shopId) {

		return shopRepository.findById(shopId).orElse(null);
	}

	// 공지찾기
	@Transactional(readOnly = true)
	public Notice findNotice(Long noticeId) {

		return noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);
	}

	// 공지 좋아요 찾기
	@Transactional(readOnly = true)
	public NoticeLike findNoticeLike(Long notiLikeId) {

		return noticeLikeRepository.findById(notiLikeId).orElseThrow(EntityNotFoundException::new);
	}

	// 매장공지 좋아요 insert
	public NoticeLike saveNoticeLike(NoticeLike noticeLike) {

		return noticeLikeRepository.save(noticeLike);
	}

	// 매장공지 좋아요 delete
	public void deleteNoticeLike(NoticeLike noticeLike) {

		noticeLikeRepository.delete(noticeLike);
	}

	// 매장공지 내용을 insert
	public Notice saveNotice(Notice notice) {

		return noticeRepository.save(notice);
	}
	
	// 매장공지 내용을 update
	public void updateNotic(Long id, NoticeDto noticeDto, Member member, Shop shop) {
		
		Notice notice = findNotice(id);
		
		notice.updateNotice(noticeDto, member, shop);
		
	}
	
	// 매장공지 내용을 delete
	public void deleteNotice(Notice notice) {

		noticeRepository.delete(notice);
	}

	/* 근무일지 */

	// 매장공지 내용을 가져옴
	@Transactional(readOnly = true)
	public List<WorkLogDto> getLogList(Long shopId) {

		List<WorkLog> workLogList = workLogRepository.findByShopIdOrderByIdDesc(shopId);
		List<WorkLogDto> workLogDtoList = new ArrayList<>();

		for (WorkLog workLog : workLogList) {

			WorkLogDto workLogDto = new WorkLogDto();

			workLogDto.setWorkLogId(workLog.getId());
			workLogDto.setMemberFormDto(getMember(workLog.getMember()));
			workLogDto.setLogCont(workLog.getLogCont());
			workLogDto.setRegTime(workLog.getRegTime());
			workLogDto.setUpdateTime(workLog.getUpDateTime());
			workLogDto.setPartTime(getPartTime(workLog.getMember(), workLog.getShop()));
			
			workLogDtoList.add(workLogDto);
		}

		return workLogDtoList;
	}
	
	// shopId랑 memeberId로 mapping 파트타임구하기
	public String getPartTime(Member member, Shop shop) {
		
		MemShopMapping mapping = mapRepository.findByMemberIdAndShopId(member.getId(), shop.getId());
		return mapping.getPartTime();
	}
	
	// 공지찾기
	@Transactional(readOnly = true)
	public WorkLog findWorkLog(Long workLogId) {

		return workLogRepository.findById(workLogId).orElseThrow(EntityNotFoundException::new);
	}

	// 근무일지 내용을 insert
	public WorkLog saveWorkLog(WorkLog workLog) {

		return workLogRepository.save(workLog);
	}
	
	// 근무일지 내용을 update
	public void updateWorkLog(Long id, WorkLogDto workLogDto, Member member, Shop shop) {
		
		WorkLog workLog = findWorkLog(id);
		
		workLog.updateWorkLog(workLogDto, member, shop);
	}
	
	// 근무일지 내용을 delete
	public void deleteWorkLog(WorkLog workLog) {

		workLogRepository.delete(workLog);
	}
	
	
	/* 매장등록 */
	public MemShopMapping saveMemMapping(MemShopMapping memShopMapping) {
		
		return mapRepository.save(memShopMapping);
	}
	
	// 등록이 되어있는지 아닌지 체크
	public int chkMemMap(Long shopId, String userId) {
		
		Member member = findMember(userId);
		MemShopMapping mapping = mapRepository.findByMemberIdAndShopId(member.getId(), shopId);
		
		if (mapping == null) {
			
			return 0;
		} else {
			
			return 1;
		}
	}
}
