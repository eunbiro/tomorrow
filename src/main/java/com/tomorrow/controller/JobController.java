package com.tomorrow.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.PayListDto;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JobController {
	private final ShopService shopService;
	private final MemberService memberService;

	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}

	// 구인공고 등록 페이지
	@GetMapping(value = "/admin/job/new")
	public String jobNew(Model model, Principal principal) {
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("payListDto", new PayListDto());

		return "job/jobNew";
	}

	// 구인공고뷰 페이지
	@GetMapping(value = "/job/view")
	public String jobView(Model model, Principal principal) {
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("payListDto", new PayListDto());

		return "job/jobView";
	}

	// 알바신청리스트 페이지
	@GetMapping(value = "admin/job/list")
	public String jobList(Model model, Principal principal) {
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("payListDto", new PayListDto());

		return "job/jobList";
	}

	// 채용등록리스트 페이지
	@GetMapping(value = "/admin/job/openingList")
	public String jobOpeningList(Model model, Principal principal) {
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);
		myShopList.stream().forEach(System.out::println);

		model.addAttribute("myShopList", myShopList);
//		model.addAttribute("payListDto", new PayListDto());
		return "job/jobOpeningList";
	}

}
