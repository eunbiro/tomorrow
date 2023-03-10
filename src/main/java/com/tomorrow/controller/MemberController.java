package com.tomorrow.controller;

import java.security.Principal;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.auth.PrincipalDetails;
import com.tomorrow.constant.Role;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Notice;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final MemberRepository memberRepository;
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
		return "member/memberJoinForm";
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
			return "member/adminJoinForm";
		}
		
		//회원가입 버튼을 눌렀을때 실행되는 메소드
	@PostMapping(value = "/admin/new")
	public String adminForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, @RequestParam("profileImgFile") MultipartFile profileImgFile) throws Exception {
		if(bindingResult.hasErrors()) {
			return "member/adminJoinForm";
		}
		try {
			Member memberNotImg = Member.createMember(memberFormDto, passwordEncoder, Role.ADMIN);
			Member member = memberService.saveProfileImg(memberNotImg, profileImgFile);
			
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/adminJoinForm";
		}
		return "redirect:/";
	}
	
	// 카카오api
	@GetMapping(value = "/oauth/logininfo")
	public String oauthLoginInfo(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) {
		
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		System.out.println(attributes);
		
		Map<String, Object> attributes2 = oAuth2UserPrincipal.getAttributes();
		
		return attributes.toString();
	}
	
	@PostMapping(value = "/member/{memberId}/delete")
	public @ResponseBody ResponseEntity deleteMember(@PathVariable("memberId") Long memberId, Principal principal) {

		memberService.deleteMember(memberId);
		return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}

}