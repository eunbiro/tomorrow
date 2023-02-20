package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.NoticeDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
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
		return "shop/shopNoticeForm";
	}
	
	// POST매장공지 등록 시
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(@Valid NoticeDto noticeDto, Model model, BindingResult bindingResult, Principal principal) {
		
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

	// GET근무일지폼
	@GetMapping(value = {"/shop/log", "/shop/log/{shop_id}"})
	public String shopLog(Model model, Principal principal) {
		
		// TODO 현재 로그인한 회원의 매장번호를 조회해서 매장코드로 업무내용 불러옴
		
		return "shop/workLogForm";
	}
	
	// POST근무일지폼
	@PostMapping(value = "/shop/log/{shop_id}")
	public String shopLogUpdate(Model model) {
		
		return "shop/workLogForm";
	}
	
	// 직원정보(employeeInfoForm.html - 수경)
	@GetMapping(value = "/shop/employeeInfo")
	public String employeeInfo(Model model) {

		return "shop/employeeInfoForm";
	}

	// 매장생성(shopCreateForm.html)
	@GetMapping(value = "/shop/shopCreate")
	public String createShop(Model model) {
		return "shop/shopCreateForm";
		
	}
	
	// 출퇴근조회(commuteListForAdmin.html - 수경)
	@GetMapping(value = "/shop/commuteList")
	public String commuteListForAdmin() {
		return "shop/commuteListForAdmin";
	}
	
	// 급여관리(payrollManagement.html - 수경)
	@GetMapping(value = "/shop/payroll")
	public String payrollManagement() {
		return "shop/payrollManagement";
	}
	
	// 매장정보(shopInfo.html - 수경)
	@GetMapping(value = "/shop/shopInfo")
	public String shopInfo() {
		return "shop/shopInfo";
	}
}
