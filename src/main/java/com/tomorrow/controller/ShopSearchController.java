package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopInfoService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopSearchController {
	
	private final ShopService shopService;
	private final MemberService memberService;
	private final ShopInfoService shopInfoService;

	// GET매장검색폼
	@GetMapping(value = "/shop/search")
	public String shopSearch(Model model, Principal principal) {

		List<MemShopMappingDto> myShopList = shopInfoService.getMyShop(principal.getName());

		getSideImg(model, principal);
		model.addAttribute("myShopList", myShopList);
		return "shop/shopSearchForm";
	}
	
	// GET매장검색 눌렀을때
	@GetMapping(value = "/shop/search/{shopId}")
	public @ResponseBody ResponseEntity shopSearchNm(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		
		ShopDto shopDto = shopService.getShop(shopService.findShop(shopId));
		
		return new ResponseEntity (shopDto, HttpStatus.OK);
	}
	
	// 매장 등록시
	@PostMapping(value = "/shop/search/{shopId}")
	public String addShop(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		
		try {
			
			Shop shop = shopService.findShop(shopId);
			Member member = shopService.findMember(principal.getName());
			MemShopMapping mapping = MemShopMapping.createMemMapping(shop, member);
			shopService.saveMemMapping(mapping);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "매장등록 중 에러가 발생했습니다.");
			return "shop/shopSearchForm";
		}
		
		return "redirect:/shop/search";
	}
	
	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}
}
