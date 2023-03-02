package com.tomorrow.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Member;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FindMemberController {

	@Autowired
	private final MemberService memberService;
	
	private final PasswordEncoder passwordEncoder;

	// ID/PASSWORD 찾기화면
	@GetMapping(value = "/find/info")
	public String findMemberInfo(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/findMemberInfo";
	}
	
	/* -------------------------------------------------------- FIND ID ------------------------------------------------- */
	
	// ID찾기
	@PostMapping(value = "/find/info/id")
	public String memberFindId(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

		/*
		 if (!memberService.isPhoneNum(memberFormDto.getPNum())) {
		 model.addAttribute("errorPhone", "전화번호를 형식대로 입력해주세요!");
		 return "member/findMemberInfo";
		 }
		*/
		try {
			 Member memberFindID = memberService.findNmPhone(memberFormDto.getUserNm(), memberFormDto.getPNum()); 
			 
			 if (memberFindID == null) {
				 model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
				 return "member/findMemberInfo";
			 } else {
				 model.addAttribute("findID", memberFindID);				 
			 }
			 
			// Post방식으로 다음 페이지 넘어감
			return "member/memberFindIdResult";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다.");
			return "member/findMemberInfo";
		}
	}
	
	/* -------------------------------------------------------- FIND ID RESULT ------------------------------------------------- */

	// Id 찾기 페이지에서 Post 방식으로 정보 전달 메소드
	/*
	@PostMapping(value = "/find/result/id")
	public String memberFindResultId(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		Member memberFindID = memberService.findNmPhone(memberFormDto.getUserNm(), memberFormDto.getPNum());
		model.addAttribute("findID", memberFindID);
		// System.out.println(memberFindID.getUserId());
		return "member/memberFindIdResult";
	}
	*/
	
	/* -------------------------------------------------------- FIND PASSWORD ------------------------------------------------- */
	
	// PASSWORD 찾기
		@PostMapping(value = "/find/info/password")
		public String memberFindPassword(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
	
			/*
			 if (!memberService.isPhoneNum(memberFormDto.getPNum())) {
			 model.addAttribute("errorPhone", "전화번호를 형식대로 입력해주세요!");
			 return "member/findMemberInfo";
			 }
			*/
			try {
				Member memberFindPASS = memberService.findIdPhone(memberFormDto.getUserId(), memberFormDto.getPNum());
				model.addAttribute("findPassword", memberFindPASS);
				model.addAttribute("memberFormDto", memberFormDto);
				// Post방식으로 다음 페이지 넘어감
				return "member/modifyPassword";
			} catch (Exception e) {
				model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
				return "member/findMemberInfo";
			}
		}
		
		/* -------------------------------------------------------- PASSWORD MODIFY  ------------------------------------------------- */
		
		//PASSWORD 입력 수정 메소드
		@PostMapping(value = "/find/modify")
		public String memberPassModify(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

			try {
				memberService.newPassword(memberFormDto, passwordEncoder);
			} catch (Exception e) {
				e.printStackTrace();
				return "member/modifyPassword";
			}
			return "redirect:/intro";
		}
		
}
