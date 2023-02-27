package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopInfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopInfoController {
	private final MemberService memberService;
	private final ShopInfoService shopInfoService;
	// 원래 마이샵은 상품 등록/수정 페이지랑 상품 상세정보 페이지로 나누어져 있었는데 
	// 여기는... 상품 등록과 상품 상세정보/수정 페이지로 구분합시다. 
	// 상품 등록은 정환님이 하니깐... 아 뭔가 마땅한 방법같지 않은데 일단 이렇게 고고
	// 정환님 페이지 만들고 있는데 거기다가 내가 수정을 끼워넣을 수는 없으니깐 ! ㅎㅎ 
	
	
	/* TODO 
	 * 1. 수정 폼을 새로 만든다. (사실 복붙해서 만들었으니까 약간 손만 보면 됨)
	 * 2. 수정 메소드들 잘 작동하는지 확인
	 * 3. 조회 폼은 form 쓰는 거 아니니까 업데이트 잘 되면 html 수정 (*->$)
	 * 
	 * */
	
	
	/* TODO 수정기능 넣고 난 다음에 할 일 
	 * 1. 매장 상세정보 페이지 html 수정하기 (input -> readonly, border = none 해서 피그마처럼 만들기)
	 * 2. 정환님 코드 보고 지도 불러오기 (도전? 아니면 굳이?)
	 * 3. 사진 누르면 원본 크기로 사진 창 따로 뜨게 만들기! (도전) 
	 * */
	
	// 매장정보폼 불러오기
	@GetMapping(value = "/shop/shopInfo")
	public String shopInfoForAdmin(Model model, Principal principal) {
		List<MemShopMappingDto> myShopList = shopInfoService.getMyShop(principal.getName());

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("shopDto", new ShopDto());
		getSideImg(model, principal);
		return "shop/shopInfo";
	}

	// 매장 정보 불러오기
	@GetMapping(value = "/shop/shopInfo/{shopId}")
	public String getShopInfoForAdmin(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		getSideImg(model, principal);

		List<MemShopMappingDto> myShopList = shopInfoService.getMyShop(principal.getName());

		Shop shop = shopInfoService.findShop(shopId);
		ShopDto shopDto = shopInfoService.getShop(shop);

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("shopDto", shopDto);

		return "shop/shopInfo";
	}
	
	/* TODO
	 * 1. 매장정보 수정 페이지 불러오기
	 * 2. 매장정보 수정 
	 * */
	/*
	// 매장정보 수정 페이지 보기 
	@GetMapping(value = "/shop/shopInfoEdit/{shopId}")
	public String shopDtl(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		try {
			getSideImg(model, principal);
			CreateShopFormDto createShopFormDto = shopInfoService.getShopInfoDtl(shopId);
			model.addAttribute(createShopFormDto);
		} catch(EntityNotFoundException e) {
			model.addAttribute("createShopFormDto", new CreateShopFormDto());
			return "shopCreate/shopCreat";
		}
		
		return "shop/shopInfoEdit";
	}
	
	// 매장정보 수정
	@PostMapping(value = "shop/shopInfoEdit/{shopId}")
	public String itemUpdate(@Valid CreateShopFormDto createShopFormDto, BindingResult bindingResult, Model model, @RequestParam("shopImgFile") List<MultipartFile> shopImgFileList, Principal principal) {
		try {
			getSideImg(model, principal);
			shopInfoService.updateShopInfo(createShopFormDto, shopImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "매장 정보 수정 중 에러가 발생하였습니다.");
		}
		return "redirect:/";
	}
	*/
	
	

	// 직원정보 - 수경 2
	@GetMapping(value = "/shop/employeeInfo")
	public String employeeInfo(Model model, Principal principal) {
		getSideImg(model, principal);
		return "shop/employeeInfoForm";
	}

	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}

}
