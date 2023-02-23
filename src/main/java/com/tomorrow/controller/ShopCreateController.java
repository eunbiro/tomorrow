package com.tomorrow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.service.ShopCreateService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopCreateController {

	private final ShopCreateService shopCreateService;
	//매장생성(shopCreateForm.html) 들어가기
		@GetMapping(value="/shopCreate/shopCreate")
		public String createShopForm(Model model) {
			model.addAttribute("createShopFormDto",new CreateShopFormDto());
			return "shop/shopCreateForm";
		}
		// 매장생성(shopCreateForm.html) 진짜 생성
		@PostMapping(value = "/shopCreate/shopCreate")
		public String createShop(@Valid CreateShopFormDto createShopFormDto, BindingResult bindingResult, 
				Model model, @RequestParam("createShopImgFile") List<MultipartFile> createShopImgFileList) {
			
			if(bindingResult.hasErrors()) {
				return "shop/shopCreateForm";
			}
			if(createShopImgFileList.get(0).isEmpty() && createShopFormDto.getId() == null) {
				model.addAttribute("errorMessage","첫 번째 상품 이미지는 필수 입력 값 입니다.");
				return "shop/shopCreateForm";
			}
			
			try {
				shopCreateService.saveShop(createShopFormDto, createShopImgFileList);
			}catch(Exception e) {
				model.addAttribute("errorMessage","상품 등록 중 에러가 발생했습니다.");
				return "shop/shopCreateForm";
			}
			
			return "redirect:/";
		}
}
