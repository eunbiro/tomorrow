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

		try {
			 Member memberFindID = memberService.findNmPhone(memberFormDto.getUserNm(), memberFormDto.getPNum()); 
			 
			 if (memberFindID == null) {
				 model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
				 return "member/findMemberInfo";
			 } else {
				 model.addAttribute("findID", memberFindID);				 
			 }
			return "member/memberFindIdResult";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다.");
			return "member/findMemberInfo";
		}
	}
	
	/* -------------------------------------------------------- FIND PASSWORD ------------------------------------------------- */
	
	// PASSWORD 찾기
		@PostMapping(value = "/find/info/password")
		public String memberFindPassword(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
	
			try {
				Member memberFindPASS = memberService.findIdPhone(memberFormDto.getUserId(), memberFormDto.getPNum());
				
				if (memberFindPASS == null) {
					model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
					return "member/findMemberInfo";
				 } else {
					 model.addAttribute("memberFindPASS", memberFindPASS);
					 model.addAttribute("memberFormDto", memberFormDto);
				 }
				return "member/passwordHint";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다.");
				return "member/findMemberInfo";
			}
		}
		
		/* -------------------------------------------------------- PASSWORD HINT  ------------------------------------------------- */
		
		@PostMapping(value ="/find/hint")
		public String memberHint(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
			
			Member memberHint = memberService.findMemberHint(memberFormDto.getHintA() ,memberFormDto.getUserId(), memberFormDto.getPNum());
			try {
			
				//하기 전에 memberFormDto에 있는 정보를 전에 있던 정보와 같이 끌어와야 한다 (Service에 저장 메소드를 만들자)
				if (memberHint.getHintA() == null) {
					model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
					return "member/passwordHint";
				} else {
					model.addAttribute("memberHint", memberHint);
				}
				return "member/modifyPassword";
			} catch (Exception e) {
				model.addAttribute("errorMessage", "오류가 발생하였습니다.");
				return "main";
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
