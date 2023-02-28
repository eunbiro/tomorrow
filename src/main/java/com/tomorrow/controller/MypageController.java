package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopCheckDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopCheckService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberService memberService;
	private final ShopCheckService shopCheckService;
	private final ShopService shopService;
	
	
	
	// 마이페이지
	@GetMapping(value = "/member/mypage")
	public String myPageForm(Model model, Principal principal) {
		getSideImg(model, principal);
		List<MemShopMappingDto> memShopMappingDtoList = shopCheckService.getMemShop(principal.getName());
		
		model.addAttribute("memShopMappingDtoList",memShopMappingDtoList);
		
		return "member/myPage";
	}
	//사이드바 프로필 이미지 가져오기 
	public Model getSideImg(Model model, Principal principal) {   
	     MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
	     return model.addAttribute("member", memberFormDto);
	}
	
	//프로필 정보 가져오기
}
