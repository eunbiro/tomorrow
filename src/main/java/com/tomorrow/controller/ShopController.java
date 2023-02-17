package com.tomorrow.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tomorrow.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopController {

	// 매장공지폼
	@GetMapping(value = "/shop/info")
	public String shopInfo(Model model) {
		
		model.addAttribute("noticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}
	
	// 매장공지폼
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(Model model) {
		
		return "shop/shopNoticeForm";
	}
	
	// 근무일지폼
	@GetMapping(value = {"/shop/log", "/shop/log/{shop_id}"})
	public String shopLog(Model model) {
		
		return "shop/workLogForm";
	}
	
	// 직원정보폼
	@GetMapping(value = "/shop/employeeInfo")
	public String employeeInfo(Model model) {
		
		return "shop/employeeInfoForm";
	}
}
