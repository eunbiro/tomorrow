package com.tomorrow.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
			 Member memberFindID = memberService.findNmPhone(memberFormDto.getUserNm(),
			 memberFormDto.getPNum()); model.addAttribute("findID", memberFindID);
			// Post방식으로 다음 페이지 넘어감
			return "member/memberFindIdResult";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
			return "member/findMemberInfo";
		}
	}

	// Id 찾기 페이지에서 Post 방식으로 정보 전달 메소드
	@PostMapping(value = "/find/result/id")
	public String memberFindResultId(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		Member memberFindID = memberService.findNmPhone(memberFormDto.getUserNm(), memberFormDto.getPNum());
		model.addAttribute("findID", memberFindID);
		// System.out.println(memberFindID.getUserId());
		return "member/memberFindIdResult";
	}
	
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
				Member memberFindPASS = memberService.findEmailPhone(memberFormDto.getUserId(), memberFormDto.getPNum());
				model.addAttribute("findPassword", memberFindPASS);
//				model.addAttribute("memberFormDto", new MemberFormDto());
				// Post방식으로 다음 페이지 넘어감
				return "member/modifyPassword";
			} catch (Exception e) {
				model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
				return "member/findMemberInfo";
			}
		}
		
		/* -------------------------------------------------------- PASSWORD MODIFY  ------------------------------------------------- */
		
	
		//PASSWORD 찾기 페이지에서 Post 방식으로 정보 전달 메소드
		@PostMapping(value = "/find/result/password")
		public String memberFindResultPassword(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
			Member memberFindPASS = memberService.findEmailPhone(memberFormDto.getUserId(), memberFormDto.getPNum());
			model.addAttribute("findPassword", memberFindPASS);
			model.addAttribute("memberFormDto", memberFormDto);
			// System.out.println(memberFindID.getUserId());
			return "member/modifyPassword";
		}
		
		//PASSWORD 입력 수정 메소드
		@PostMapping(value = "/find/modify")
		public String memberPassModify(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

			try {
				memberService.updatePassword(memberFormDto, passwordEncoder);
			} catch (Exception e) {
				model.addAttribute("msg", "비밀번호를 다시 확인해 주세요.");
				return "member/modifyPassword";
			}
			return "main";
		}
		
}
