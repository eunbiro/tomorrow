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
			memberFormDto = memberService.findNmPhone(memberFormDto.getUserNm(), memberFormDto.getPNum());
			
			 if (memberFormDto == null) {
				 model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
				 return "member/findMemberInfo";
			 } else {
				 model.addAttribute("findID", memberFormDto);				
			 }
			return "member/memberFindIdResult";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다.");
			return "member/findMemberInfo";
		}
	}
	
	/* -------------------------------------------------------- FIND PASSWORD ------------------------------------------------- */
	
	// PASSWORD 찾기
		//@SuppressWarnings("unused")
		@PostMapping(value = "/find/info/password")
		public String memberFindPassword(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
	
				MemberFormDto memberDto = new MemberFormDto();
				memberDto = memberService.haveMemberInfo(memberFormDto.getUserId(), memberFormDto.getPNum());
				
				if (memberDto == null) {
					model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
					 return "member/findMemberInfo";
				} else {
					memberDto.setHintA(null);
					model.addAttribute("memberFormDto", memberDto);
				}
				return "member/passwordHint";
		}
		
		/* -------------------------------------------------------- PASSWORD HINT  ------------------------------------------------- */
		
		@PostMapping(value ="/find/hint")
		public String memberHint(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
			
			MemberFormDto memberDto = new MemberFormDto();
			memberDto = memberService.findMemberHint(memberFormDto.getHintA(), memberFormDto.getHintQ(), memberFormDto.getUserId());
			
			if (memberDto == null) {
				model.addAttribute("errorMessage", "일치하지 않습니다.");
				return "member/passwordHint";
			} else {
				model.addAttribute("memberFormDto", memberDto);
			}
			return "member/modifyPassword";
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