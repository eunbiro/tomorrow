package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.PasswordDto;
import com.tomorrow.dto.ShopCheckDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.MyPageService;
import com.tomorrow.service.ShopCheckService;
import com.tomorrow.service.ShopService;

import javassist.expr.NewArray;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberService memberService;
	private final ShopCheckService shopCheckService;
	private final PasswordEncoder passwordEncoder;
	private final MyPageService myPageService;
	private final MemberRepository memberRepository;

	// 마이페이지 (매장)
	@GetMapping(value = "/member/mypage")
	public String myPageForm(Model model, Principal principal) {

		// 사이드 이미지 가져오기
		getSideImg(model, principal);

		// dto 타입으로 된 리스트에 넣기 (principal.getName() = userId);
		List<MemShopMappingDto> memShopMappingDtoList = shopCheckService.getMemShop(principal.getName());

		// 가져온 리스트를 "memShopMappingDtoList로 이름 부여
		model.addAttribute("memShopMappingDtoList", memShopMappingDtoList);
		model.addAttribute("passwordDto", new PasswordDto());
		return "member/myPage";
	}

	// 마이페이지에서 비밀번호 변경 페이지 가기 전 비밀번호 확인 절차
	@PostMapping(value = "/member/passwordcheck")
	public String comparePassword(@Valid PasswordDto passwordDto, Principal principal, Model model) {

		//passwordDto에 담겨진 checkPassword랑 원래 멤버의 비밀번호랑 비교 할거임 
		if (myPageService.comparePassword(passwordDto, principal.getName()) == 1) {
			return "redirect:/member/myPagePassword";
		}
		// 사이드 이미지 가져오기
		getSideImg(model, principal);

		// dto 타입으로 된 리스트에 넣기 (principal.getName() = userId);
		List<MemShopMappingDto> memShopMappingDtoList = shopCheckService.getMemShop(principal.getName());

		// 가져온 리스트를 "memShopMappingDtoList로 이름 부여
		model.addAttribute("memShopMappingDtoList", memShopMappingDtoList);
		model.addAttribute("passwordDto", new PasswordDto());
		model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
		return "member/myPage";

	}

	// 사이드바 프로필 이미지 가져오기
	public Model getSideImg(Model model, Principal principal) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}

	// 마이페이지 (패스워드)
	@GetMapping(value = "/member/myPagePassword")
	public String myPagePasswordForm(Model model, Principal principal) {
		Member member = findMember(principal.getName());
		getSideImg(model, principal);
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setPNum(member.getPNum());
		model.addAttribute("member", member);
		model.addAttribute("memberFormDto", memberFormDto);
		return "member/myPagePassword";
	}

	// 마이페이지 (패스워드 바꾸기);
	@PostMapping(value = "/member/passwordChange")
	public String memberPasswordModify(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model,
			Principal principal) {
		try {
			memberService.newPasswordpNum(memberFormDto, passwordEncoder);

		} catch (Exception e) {
			e.printStackTrace();
			return "member/myPagePassword";
		}
		return "redirect:/intro";
	}

	@Transactional(readOnly = true)
	public Member findMember(String userId) {

		return memberRepository.findByUserId(userId);
	}

}
