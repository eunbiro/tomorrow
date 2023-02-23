package com.tomorrow.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.AdminFormDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Member;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	// 로그인 화면
	@GetMapping(value = "/member/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}

	// 로그인을 실패했을때
	@GetMapping(value = "/member/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}

	// 회원가입 경로
	@GetMapping(value = "/member/join")
	public String joinForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "/member/memberJoinForm";
	}
	
	//회원가입 버튼을 눌렀을때 실행되는 메소드
	@PostMapping(value = "/member/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, @RequestParam("profileImgFile") MultipartFile profileImgFile) throws Exception {
		if(bindingResult.hasErrors()) {
			return "member/memberJoinForm";
		}
		try {
			Member memberNotImg = Member.createMember(memberFormDto, passwordEncoder, Role.USER);
			Member member = memberService.saveProfileImg(memberNotImg, profileImgFile);
			
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberJoinForm";
		}
		return "redirect:/";
	}
	
	/* 회원가입 */
	
	// 회원가입 경로
		@GetMapping(value = "/admin/join")
		public String adminJoinForm(Model model) {
			model.addAttribute("memberFormDto", new MemberFormDto());
			return "/member/memberJoinForm";
		}
		
		//회원가입 버튼을 눌렀을때 실행되는 메소드
	@PostMapping(value = "/admin/new")
	public String adminForm(@Valid AdminFormDto adminFormDto, BindingResult bindingResult, Model model, @RequestParam("profileImgFile") MultipartFile profileImgFile) throws Exception {
		if(bindingResult.hasErrors()) {
			return "member/adminJoinForm";
		}
		try {
			Member memberNotImg = Member.createAdmin(adminFormDto, passwordEncoder, Role.ADMIN);
			Member member = memberService.saveProfileImg(memberNotImg, profileImgFile);
			
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/adminJoinForm";
		}
		return "redirect:/";
	}
	
		//마이페이지
	@GetMapping(value = "/member/mypage")
	public String myPageForm() {
		return "member/myPage";
	}
}