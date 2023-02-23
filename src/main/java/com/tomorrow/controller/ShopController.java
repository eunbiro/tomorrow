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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.NoticeDto;
import com.tomorrow.dto.NoticeLikeDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
import com.tomorrow.entity.NoticeLike;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopController {

	private final ShopService shopService;

	// GET매장공지폼
	@GetMapping(value = "/shop/info")
	public String shopInfo(Model model, Principal principal) {

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("noticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}

	// GET매장 선택 시 공지내역가져옴
	@GetMapping(value = "/shop/info/{shopId}")
	public String shopGetNoti(@PathVariable("shopId") String shopId, Model model, Principal principal) {

		List<NoticeDto> notiList = shopService.getNoticeList(shopId);
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
		NoticeDto noticeDto = new NoticeDto();
		ShopDto shopDto = new ShopDto();
		shopDto.setShopId(Long.parseLong(shopId));
		noticeDto.setShopDto(shopDto);

		model.addAttribute("notiList", notiList);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("noticeDto", noticeDto);
		model.addAttribute("updateNoticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}

	// POST매장공지 등록 시
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(@Valid NoticeDto noticeDto, Model model, BindingResult bindingResult,
			Principal principal) {

		if (bindingResult.hasErrors()) {

			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

			model.addAttribute("myShopList", myShopList);
			return "shop/shopNoticeForm";
		}

		try {

			Member member = shopService.findMember(principal.getName());
			Long ShopId = noticeDto.getShopDto().getShopId();
			Notice notice = Notice.createNotice(noticeDto, member, shopService.findShop(ShopId));
			shopService.saveNotice(notice);

		} catch (Exception e) {

			model.addAttribute("errorMessage", "공지등록 중 에러가 발생했습니다.");
			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

			model.addAttribute("myShopList", myShopList);
			model.addAttribute("noticeDto", noticeDto);
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
	@PostMapping(value = "/shop/info/{noticeId}/update")
	public String updateNoticePage(@PathVariable("noticeId") Long noticeId, @Valid NoticeDto noticeDto, Model model, BindingResult bindingResult, Principal principal) {
		
		if (bindingResult.hasErrors()) {
			
			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
			
			model.addAttribute("myShopList", myShopList);
			return "shop/shopNoticeForm";
		}
		
		try {
			
			Notice notice = shopService.findNotice(noticeId);
			notice.setNoticeCont(noticeDto.getNoticeCont());
			Notice.updateNotice(notice);
			
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "공지등록 중 에러가 발생했습니다.");
			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
			
			model.addAttribute("myShopList", myShopList);
			model.addAttribute("noticeDto", noticeDto);
			return "redirect:/shop/info/" + noticeDto.getShopDto().getShopId();
		}
		
		
		return "redirect:/shop/info/" + noticeDto.getShopDto().getShopId();
	}
	
	// 공지 삭제 눌렀을때
	@DeleteMapping(value = "/shop/notice/{noticeId}/delete")
	public @ResponseBody ResponseEntity deleteNotice(@PathVariable("noticeId") Long noticeId, Principal principal) {

		Notice notice = shopService.findNotice(noticeId);
		shopService.deleteNotice(notice);
		return new ResponseEntity<Long>(noticeId, HttpStatus.OK);
	}

	// GET근무일지폼
	@GetMapping(value = { "/shop/log", "/shop/log/{shopId}" })
	public String shopLog(Model model, Principal principal) {

		// TODO 현재 로그인한 회원의 매장번호를 조회해서 매장코드로 업무내용 불러옴

		return "shop/workLogForm";
	}

	// POST근무일지폼
	@PostMapping(value = "/shop/log/{shopId}")
	public String shopLogUpdate(Model model) {

		return "shop/workLogForm";
	}

	// 직원정보 - 수경 2
	@GetMapping(value = "/shop/employeeInfo")
	public String employeeInfo(Model model) {

		return "shop/employeeInfoForm";
	}
		

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

	/*
	 * TODO 1. 매장 정보 폼 가져오기 (SELECT) 2. 매장 정보 가져오기 3. 매장 정보 수정하기 4. '취소'버튼 누르면
	 * 마이페이지로 가게 하기
	 */

	// 매장정보폼 불러오기 - 수경 1
	@GetMapping(value = "/shop/shopInfo")
	public String shopInfoForAdmin(Model model, Principal principal) {
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("shopDto", new ShopDto());

		return "shop/shopInfo";
	}

	// 매장 선택 시 매장 정보 내역 가져옴
	@GetMapping(value = "/shop/shopInfo/{shopId}")
	public String getShopInfoForAdmin(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		Shop shop = shopService.findShop(shopId);
		ShopDto shopDto = shopService.getShop(shop);

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("shopDto", shopDto);

		return "shop/shopInfo";
	}

}
