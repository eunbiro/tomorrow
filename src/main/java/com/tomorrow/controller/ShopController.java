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

import com.tomorrow.dto.NoticeDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Manager;
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
		
		List<ShopDto> myShopList = shopService.getMyShop(principal.getName());
		
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("noticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}
	
	// GET매장 선택 시 공지내역가져옴
	@GetMapping(value = "/shop/noti/{shopId}")
	public String shopGetNoti(@PathVariable("shopId") String shopId, NoticeDto noticeDto, Model model, Principal principal) {
		
		List<NoticeDto> notiList = shopService.getNoticeList(shopId);
		List<ShopDto> myShopList = shopService.getMyShop(principal.getName());
		
		model.addAttribute("notiList", notiList);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("noticeDto", noticeDto);
		return "shop/shopNoticeForm";
	}
	
	// POST매장공지폼
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(@Valid NoticeDto noticeDto, Model model, BindingResult bindingResult, Principal principal) {
		
		if (bindingResult.hasErrors()) {
			
			List<ShopDto> myShopList = shopService.getMyShop(principal.getName());
			
			model.addAttribute("myShopList", myShopList);
			return "shop/shopNoticeForm";
		}
		
		try {
			
			Manager manager = shopService.findManager(principal.getName());
			Notice notice = Notice.createNotice(noticeDto, manager, noticeDto.getShopDto().getShopId());
			shopService.saveNotice(notice);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "공지등록 중 에러가 발생했습니다.");
			List<ShopDto> myShopList = shopService.getMyShop(principal.getName());
			
			model.addAttribute("myShopList", myShopList);
			model.addAttribute("noticeDto", noticeDto);
			return "shop/shopNoticeForm";
		}
		
		
		return "redirect:/shop/shopNoticeForm";
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
	
	// 직원정보폼
	@GetMapping(value = "/shop/employeeInfo")
	public String employeeInfo(Model model) {

		return "shop/employeeInfoForm";
	}

	// 매장생성(shopCreateForm.html)
	@GetMapping(value = "/shop/shopCreate")
	public String createShop(Model model) {
		return "shop/shopCreateForm";
		
	}
}
