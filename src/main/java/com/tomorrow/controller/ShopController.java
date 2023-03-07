package com.tomorrow.controller;


import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.NoticeDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.dto.WorkLogDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
import com.tomorrow.entity.Shop;
import com.tomorrow.entity.WorkLog;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopController {

	private final ShopService shopService;
	private final MemberService memberService;

	// GET매장공지폼
	@GetMapping(value = "/shop/info")
	public String shopInfo(Model model, Principal principal) {

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("noticeDto", new NoticeDto());
		model.addAttribute("updateNoticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}
	
	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}

	@GetMapping(value = "/shop/shopChk/{shopId}")
	public @ResponseBody ResponseEntity shopChk(@PathVariable("shopId") Long shopId, Principal principal) {

		Member member = shopService.findMember(principal.getName());
		int chkNum = shopService.shopChk(shopId, member.getId());
		return new ResponseEntity<Integer>(chkNum, HttpStatus.OK);
	}
	
	// GET매장 선택 시 공지내역가져옴
	@GetMapping(value = "/shop/info/{shopId}")
	public String shopGetNoti(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		
		List<NoticeDto> notiList = shopService.getNoticeList(shopId);
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
		NoticeDto noticeDto = new NoticeDto();
		ShopDto shopDto = new ShopDto();
		shopDto.setShopId(shopId);
		noticeDto.setShopDto(shopDto);

		getSideImg(model, principal);
		model.addAttribute("notiList", notiList);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("noticeDto", noticeDto);
		model.addAttribute("updateNoticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}

	// POST매장공지 등록 시
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(@Valid NoticeDto noticeDto, BindingResult bindingResult, Model model, Principal principal) {

		if (bindingResult.hasErrors()) {
			
			Long shopId = noticeDto.getShopDto().getShopId();
			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
			List<NoticeDto> notiList = shopService.getNoticeList(shopId);
			
			getSideImg(model, principal);
			model.addAttribute("notiList", notiList);
			model.addAttribute("myShopList", myShopList);
			model.addAttribute("noticeDto", noticeDto);
			model.addAttribute("updateNoticeDto", new NoticeDto());
			return "shop/shopNoticeForm";
		}

		try {

			Member member = shopService.findMember(principal.getName());
			Shop shop = shopService.findShop(noticeDto.getShopDto().getShopId());
			Notice notice = Notice.createNotice(noticeDto, member, shop);
			shopService.saveNotice(notice);

		} catch (Exception e) {

			model.addAttribute("errorMessage", "공지등록 중 에러가 발생했습니다.");
			
			return "redirect:/shop/info/" + noticeDto.getShopDto().getShopId();
		}
		
		return "redirect:/shop/info/" + noticeDto.getShopDto().getShopId();
	}

//	// 좋아요 눌렀을때
//	@PostMapping(value = "/shop/info/like/{memberId}/like")
//	public ResponseEntity shopLikeInsert(@PathVariable("memberId") Long memberId, Long noticeId, Model model, Principal principal) {
//		
//		Notice notice = shopService.findNotice(noticeId);
//		Member member = shopService.findMember(principal.getName());
//		NoticeLike noticeLike = NoticeLike.createNoticeLike(member, notice);
//		shopService.saveNoticeLike(noticeLike);
//		
//		return new ResponseEntity(memberId, HttpStatus.OK);
//	}
//	
//	// 좋아요 또 눌렀을때
//	@DeleteMapping(value = "/shop/info/{notiLikeId}/likeDel")
//	public ResponseEntity shopLikeDelete(@PathVariable("notiLikeId") Long notiLikeId, Long noticeId, Model model, Principal principal) {
//		
//		NoticeLike noticeLike = shopService.findNoticeLike(notiLikeId);
//		
//		return new ResponseEntity(notiLikeId, HttpStatus.OK);
//	}
	
	// 공지 수정 눌렀을때
	@PostMapping(value = "/shop/notice/{noticeId}/update")
	public String updateNoticePage(@PathVariable("noticeId") Long noticeId, @Valid NoticeDto updateNoticeDto, BindingResult bindingResult, Model model, Principal principal) {
		
		if (bindingResult.hasErrors()) {
			
			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
			
			getSideImg(model, principal);
			model.addAttribute("myShopList", myShopList);
			return "shop/shopNoticeForm";
		}
		
		Notice notice = shopService.findNotice(noticeId);
		notice.setNoticeCont(updateNoticeDto.getNoticeCont());
		Shop shop = shopService.findShop(notice.getShop().getId());
		Member member = shopService.findMember(principal.getName());
		
		try {
			
			shopService.updateNotic(noticeId, updateNoticeDto, member, shop);
			
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "공지등록 중 에러가 발생했습니다.");

			return "redirect:/shop/info/" + shop.getId();
		}
		
		return "redirect:/shop/info/" + shop.getId();
	}
	
	// 공지 삭제 눌렀을때
	@DeleteMapping(value = "/shop/notice/{noticeId}/delete")
	public @ResponseBody ResponseEntity deleteNotice(@PathVariable("noticeId") Long noticeId, Principal principal) {

		Notice notice = shopService.findNotice(noticeId);
		shopService.deleteNotice(notice);
		return new ResponseEntity<Long>(noticeId, HttpStatus.OK);
	}
	
	/* 근무일지 폼*/

	// GET근무일지폼
	@GetMapping(value = "/shop/log")
	public String shopLog(Model model, Principal principal) {

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("workLogDto", new WorkLogDto());
		model.addAttribute("updateWorkLogDto", new WorkLogDto());
		return "shop/workLogForm";
	}
	
	// GET매장 선택 시 근무일지가져옴
	@GetMapping(value = "/shop/log/{shopId}")
	public String shopGetLog(@PathVariable("shopId") Long shopId, Model model, Principal principal) {

		List<WorkLogDto> logList = shopService.getLogList(shopId);
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
		WorkLogDto workLogDto = new WorkLogDto();
		ShopDto shopDto = new ShopDto();
		shopDto.setShopId(shopId);
		workLogDto.setShopDto(shopDto);

		getSideImg(model, principal);
		model.addAttribute("logList", logList);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("workLogDto", workLogDto);
		model.addAttribute("updateWorkLogDto", new WorkLogDto());
		return "shop/workLogForm";
	}

	// POST근무일지폼
	@PostMapping(value = "/shop/log")
	public String shopLogUpdate(@Valid WorkLogDto workLogDto, BindingResult bindingResult, Model model, Principal principal) {
		
		if (bindingResult.hasErrors()) {

			Long shopId = workLogDto.getShopDto().getShopId();
			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
			List<WorkLogDto> logList = shopService.getLogList(shopId);
			
			getSideImg(model, principal);
			model.addAttribute("logList", logList);
			model.addAttribute("myShopList", myShopList);
			model.addAttribute("workLogDto", workLogDto);
			model.addAttribute("updateWorkLogDto", new WorkLogDto());
			return "shop/workLogForm";
		}

		try {

			Member member = shopService.findMember(principal.getName());
			Shop shop = shopService.findShop(workLogDto.getShopDto().getShopId());
			WorkLog workLog = WorkLog.createWorkLog(workLogDto, member, shop);
			shopService.saveWorkLog(workLog);

		} catch (Exception e) {

			model.addAttribute("errorMessage", "공지등록 중 에러가 발생했습니다.");
			
			return "redirect:/shop/log/" + workLogDto.getShopDto().getShopId();
		}
		
		return "redirect:/shop/log/" + workLogDto.getShopDto().getShopId();
	}

	// 일지 수정 눌렀을때
	@PostMapping(value = "/shop/log/{workLogId}/update")
	public String updateWorkLog(@PathVariable("workLogId") Long workLogId, @Valid WorkLogDto updateWorkLogDto, BindingResult bindingResult, Model model, Principal principal) {
		
		if (bindingResult.hasErrors()) {
			
			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
			
			getSideImg(model, principal);
			model.addAttribute("myShopList", myShopList);
			return "shop/workLogForm";
		}
		
		WorkLog workLog = shopService.findWorkLog(workLogId);
		workLog.setLogCont(updateWorkLogDto.getLogCont());
		Shop shop = shopService.findShop(workLog.getShop().getId());
		Member member = shopService.findMember(principal.getName());
		
		try {
			
			shopService.updateWorkLog(workLogId, updateWorkLogDto, member, shop);
			
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "공지등록 중 에러가 발생했습니다.");

			return "redirect:/shop/log/" + shop.getId();
		}
		
		return "redirect:/shop/log/" + shop.getId();
	}
	
	// 일지 삭제 눌렀을때
	@DeleteMapping(value = "/shop/log/{workLogId}/delete")
	public @ResponseBody ResponseEntity deleteWorkLog(@PathVariable("workLogId") Long workLogId, Principal principal) {

		WorkLog workLog = shopService.findWorkLog(workLogId);
		shopService.deleteWorkLog(workLog);
		return new ResponseEntity<Long>(workLogId, HttpStatus.OK);
	}

	
	// 민우님 페이지입니다. 
	// 출퇴근조회
	@GetMapping(value = "/shop/commuteList")
	public String commuteListForAdmin() {
		return "shop/commuteListForAdmin";
	}

	// 급여관리
	@GetMapping(value = "/shop/payroll")
	public String payrollManagement() {
		return "shop/payrollManagement";
	}


}
