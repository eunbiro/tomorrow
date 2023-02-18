package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.ManagerDto;
import com.tomorrow.dto.NoticeDto;
import com.tomorrow.entity.Manager;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
import com.tomorrow.repository.ManagerRepository;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.noticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional 
@RequiredArgsConstructor
public class ShopService {

	private final noticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	private final ManagerRepository managerRepository;
	
	// 매장공지 내용을 가져옴
	@Transactional(readOnly = true)
	public List<NoticeDto> getNoticeList(String userId) {
		
		List<NoticeDto> noticeDtoList = new ArrayList<>();
		Member manager = memberRepository.findByUserId(userId);
		// TODO 나중에 매장등록했을때 매장정보랑 같이 조회해서 가져오는걸로 수정해야함
		List<Notice> noticeList = noticeRepository.findByManagerId(manager.getId());
		
		for (Notice notice : noticeList) {
			NoticeDto noticeDto = new NoticeDto();
			noticeDto.setManagerDto(getManager(manager));
			noticeDto.setNoticeId(notice.getId());
			noticeDto.setNoticeCont(notice.getNoticeCont());
			noticeDto.setLikeNoti(notice.getLikeNoti());
			noticeDto.setRegTime(notice.getRegTime());
			
			noticeDtoList.add(noticeDto);
		}
		
		return noticeDtoList;
	}
	
//	public Manager setManager(String userId) {
//		// TODO 원래는 manager 아이디 있으면 find해서 바로 리턴인데 임시로 멤버에서 가져와서 넣어줌
//		
//		Member member = memberRepository.findByUserId(userId);
//		Manager manager = new Manager();
//		
//		manager.setUserId(member.getUserId());
//		manager.setUserNm(member.getUserNm());
//		manager.setPassword(member.getPassword());
//		manager.setPNum(member.getPNum());
//		manager.setUserProfile(member.getUserProfile());
//		manager.setRole(Role.ADMIN);
//		
//		return manager;
//	}
//	
//	public Manager saveManager(Manager manager) {
//		
//		return managerRepository.save(manager);
//	}
	
	public Manager setManager(String userId) {
		
		return managerRepository.findByUserId(userId);
	}
	
	
	// 관리자 정보 DTO저장
	public ManagerDto getManager(Member manager) {
		
		ManagerDto managerDto = new ManagerDto();
		managerDto.setUserId(manager.getUserId());
		managerDto.setUserNm(manager.getUserNm());
		managerDto.setUserProfile(manager.getUserProfile());
		
		return managerDto;
	}
	
	// 매장공지 내용을 insert
	public Notice saveNotice(Notice notice) {
		
		return noticeRepository.save(notice);
	}
	
	
	
}
