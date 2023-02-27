package com.tomorrow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageController {
	// 마이페이지
	@GetMapping(value = "/member/mypage")
	public String myPageForm() {
		return "member/myPage";
	}
}
