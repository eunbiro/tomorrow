package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopInfoController {
	
	private final ShopService shopService;
	
	/*
	 * TODO 1. 매장 정보 폼 가져오기 (SELECT) 2. 매장 정보 가져오기 3. 매장 정보 수정하기 4. '취소'버튼 누르면
	 * 마이페이지로 가게 하기
	 */

	// 매장정보폼 불러오기 - 수경 1
	@GetMapping(value = "/shop/shopInfo")
	public String shopInfoForAdmin(Model model, Principal principal) {
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("shopDto", new ShopDto());

		return "shop/shopInfo";
	}

	// 매장 선택 시 매장 정보 내역 가져옴
	@GetMapping(value = "/shop/shopInfo/{shopId}")
	public String getShopInfoForAdmin(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		Shop shop = shopService.findShop(shopId);
		ShopDto shopDto = shopService.getShop(shop);

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("shopDto", shopDto);

		return "shop/shopInfo";
	}

}
