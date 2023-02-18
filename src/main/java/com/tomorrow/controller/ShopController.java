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

	private final ShopService shopService;
	
	// GET매장공지폼
	@GetMapping(value = "/shop/info")
	public String shopInfo(Model model, Principal principal) {
		
		List<NoticeDto> getNoticeList = shopService.getNoticeList(principal.getName());
		
		model.addAttribute("getNoticeList", getNoticeList);
		model.addAttribute("noticeDto", new NoticeDto());
		return "shop/shopNoticeForm";
	}
	
	// POST매장공지폼
	@PostMapping(value = "/shop/info")
	public String shopInfoUpdate(Model model, NoticeDto noticeDto, Principal principal) {
		
		Manager manager = shopService.setManager(principal.getName());
		Notice notice = Notice.createNotice(noticeDto, manager);
		shopService.saveNotice(notice);
		
		return "shop/shopNoticeForm";
	}

	// GET근무일지폼
	@GetMapping(value = {"/shop/log", "/shop/log/{shop_id}"})
	public String shopLog(Model model) {

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
