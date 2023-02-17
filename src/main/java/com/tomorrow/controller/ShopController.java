package com.tomorrow.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopController {

	// 매장공지폼
	@GetMapping(value = "/shop/info")
	public String shopInfo(Model model) {

		return "shop/shopNoticeForm";
	}

	// 근무일지폼
	@GetMapping(value = { "/shop", "/shop{shop_id}" })
	public String shopLog(Model model) {

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
