package com.tomorrow.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class ShopManageController {
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
		
		return "manage/employeeInfoForm";
	}


}
