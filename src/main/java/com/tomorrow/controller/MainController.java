package com.tomorrow.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private MemberService memberService;

	@GetMapping(value="/")
	public String main() {
		return "main";
	}
	
	@GetMapping(value="/intro")
	public String intro(Principal principal, Model model) {
		if (principal != null) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		model.addAttribute("member", memberFormDto);
		}
		return "intro";
	}
}
