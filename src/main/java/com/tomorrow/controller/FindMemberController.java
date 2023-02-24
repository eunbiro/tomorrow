package com.tomorrow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FindMemberController {
	
	@Autowired
	private final MemberService memberService;
	
	
	@GetMapping(value = "/find/info")
	public String findMemberInfo() {
		/* 나중에 넣을 프로필 (일단 보류)
		if (principal != null) {
			MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
			System.out.println(principal.getName());
			model.addAttribute("member", memberFormDto);
		}
		*/
		return "member/findMemberId";
	}
	
	@GetMapping(value = "/find/info/id")
	public String findMemberId() {
		/* 나중에 넣을 프로필 (일단 보류)
		if (principal != null) {
			MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
			System.out.println(principal.getName());
			model.addAttribute("member", memberFormDto);
		}
		*/
		return "member/findMemberId";
	}
	
	@GetMapping(value = "/find/info/password")
	public String findMemberPassword() {
		return "member/findMemberPassword";
	}
}
