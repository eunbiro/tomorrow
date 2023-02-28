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
import org.springframework.web.bind.annotation.RequestMapping;
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

	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}
	
	/* TODO
	 * 1. 조회하는 HTML form 태그 쓰는 거 아니라고 했던 것 같으니 나중에 확실하게 알게되면 수정
	 * 2. 지도랑 사업자번호 API 가져오기 ㅎㅎ 
	 * 3. 사진 누르면 원본 크기로 뜨게하는거 도전  
	 */

	/* 매장 조회 페이지 */
	
	// 매장정보폼 불러오기
	@GetMapping(value = "/shop/shopInfo")
	public String shopInfoForAdmin(Model model, Principal principal) {
		getSideImg(model, principal);
		List<MemShopMappingDto> myShopList = shopInfoService.getMyShop(principal.getName());

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("shopDto", new ShopDto());
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
	
	/* 매장정보 수정 페이지 */
	
	// 매장정보 수정 페이지 보기 
	@PostMapping(value = "/shop/shopInfoEdit/{shopId}")
	public String shopDtl(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		try {
			getSideImg(model, principal);
			CreateShopFormDto createShopFormDto = shopInfoService.getShopInfoDtl(shopId);
			model.addAttribute("createShopFormDto", createShopFormDto);
		} catch(EntityNotFoundException e) {
			model.addAttribute("createShopFormDto", new CreateShopFormDto());
			return "shopCreate/shopCreat";
		}
		
		return "shop/shopInfoEdit";
	}
	
	// 매장정보 수정
	@PostMapping(value = "/shop/shopEdit")
	public String shopUpdate(@Valid CreateShopFormDto createShopFormDto, @RequestParam("createShopImgFile") List<MultipartFile> shopImgFileList, BindingResult bindingResult, Model model, Principal principal) {
		getSideImg(model, principal);
		
		 if (shopImgFileList.get(0).isEmpty() && createShopFormDto.getId() == null) {
		 model.addAttribute("errorMessage", "매장 이미지는 필수 입력 값입니다."); 
		 return "shop/shopInfo"; 
		 }
		 
		try {
			shopInfoService.updateShopInfo(createShopFormDto, shopImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "매장 정보 수정 중 에러가 발생하였습니다.");
		}
		return "redirect:/";
	}

}
