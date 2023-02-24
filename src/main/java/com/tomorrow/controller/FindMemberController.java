package com.tomorrow.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Member;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FindMemberController {

	@Autowired
	private final MemberService memberService;

	@GetMapping(value = "/find/info")
	public String findMemberInfo() {
		return "member/findMemberInfo";
	}

	@PostMapping(value = "/find/info/id")
	@ResponseBody
	public String findMemberId(@RequestParam("name") String name,@RequestParam("id_phone") String id_phone) {
		String result = memberService.findId(name, id_phone);
		
		return result;
	}
	
	@PostMapping(value = "/find/info/password")
	public String findMemberPw() {
		return "member/findMemberInfo";
	}
}
