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
<<<<<<< HEAD

=======
		
		model.addAttribute("noticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}
	
	// 매장공지폼
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(Model model) {
		
>>>>>>> d58a34d1ef76269ad420ed3dd218729018c4cf6a
		return "shop/shopNoticeForm";
	}

	// 근무일지폼
<<<<<<< HEAD
	@GetMapping(value = { "/shop", "/shop{shop_id}" })
=======
	@GetMapping(value = {"/shop/log", "/shop/log/{shop_id}"})
>>>>>>> d58a34d1ef76269ad420ed3dd218729018c4cf6a
	public String shopLog(Model model) {

		return "shop/workLogForm";
	}
<<<<<<< HEAD

=======
	
	// 근무일지폼
	@PostMapping(value = "/shop/log/{shop_id}")
	public String shopLogUpdate(Model model) {
		
		return "shop/workLogForm";
	}
	
>>>>>>> d58a34d1ef76269ad420ed3dd218729018c4cf6a
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
