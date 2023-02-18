package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tomorrow.dto.NoticeDto;
import com.tomorrow.entity.Manager;
import com.tomorrow.entity.Notice;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopController {

	
	// GET매장공지폼
	@GetMapping(value = "/shop/info")
	public String shopInfo(Model model, Principal principal) {
		
		
		model.addAttribute("noticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}
	
	// POST매장공지폼
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(Model model, NoticeDto noticeDto, Principal principal) {
		
//		Manager manager = shopService.findManager(principal.getName());
//		Notice notice = Notice.createNotice(noticeDto, manager);
//		shopService.saveNotice(notice);
		
		return "shop/shopNoticeForm";
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
