package com.tomorrow.controller;

import java.security.Principal;
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
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.ShopRepository;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopCreateService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopCreateController {
	private final MemberService memberService;

	private final ShopCreateService shopCreateService;
	//매장생성(shopCreateForm.html) 들어가기
		@GetMapping(value="/shopCreate/shopCreate")
		public String createShopForm(Model model, Principal principal) { //모델은 jsp 했을 때 request espone 
			getSideImg(model, principal);
			model.addAttribute("createShopFormDto",new CreateShopFormDto());
			return "shop/shopCreateForm";
		}
		// 매장생성(shopCreateForm.html) 진짜 생성
		@PostMapping(value = "/shopcreate/	")
		public String createShop(@Valid CreateShopFormDto createShopFormDto, BindingResult bindingResult, 
				Model model, @RequestParam("createShopImgFile") List<MultipartFile> createShopImgFileList, Principal principal) {
			getSideImg(model, principal);
			
			if(bindingResult.hasErrors()) {
				return "shop/shopCreateForm";
			}
			if(createShopImgFileList.get(0).isEmpty() && createShopFormDto.getId() == null) {
				model.addAttribute("errorMessage","첫 번째 상품 이미지는 필수 입력 값 입니다.");
				return "shop/shopCreateForm";
			}
			
			try {
				//여기서 부터 봐야해
				shopCreateService.saveShop(createShopFormDto, createShopImgFileList,principal.getName());
						
			}catch(Exception e) {
				model.addAttribute("errorMessage","상품 등록 중 에러가 발생했습니다.");
				return "shop/shopCreateForm";
			}
			
			
			
			return "redirect:/";
		}
		
		//사이드바 프로필 이미지 가져오기 
		public Model getSideImg(Model model, Principal principal) {   
		     MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		     return model.addAttribute("member", memberFormDto);
		}

}
