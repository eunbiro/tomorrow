package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.service.CommuteService;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class ShopManageController {
	private final ShopService shopService;
	private final CommuteService commuteService;
	private final MemberService memberService;

	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}
	

	// 직원정보 - 수경 2
	@GetMapping(value = "/manage/employeeInfo")
	public String employeeInfo(Model model, Principal principal) {
		getSideImg(model, principal);
		return "shop/employeeInfoForm";
	}

	// 출퇴근기록 페이지
		@GetMapping(value = "/commute")
		public String commute(Model model, Principal principal) {

			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

			getSideImg(model, principal);
			model.addAttribute("myShopList", myShopList);
			model.addAttribute("commuteDto", new CommuteDto());

			return "manage/managerCommuteForm";
		}

}
