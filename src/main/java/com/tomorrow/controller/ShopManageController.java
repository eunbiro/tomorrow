package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.service.CommuteService;
import com.tomorrow.service.EmployeeInfoService;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class ShopManageController {
	private final ShopService shopService;
	private final CommuteService commuteService;
	private final MemberService memberService;
	private final EmployeeInfoService emplInfoService;
	private final MemShopMapRepository mapRepository;
	
	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}

	// 직원정보폼 불러오기
	@GetMapping(value = "/manage/employeeInfo")
	public String employeeInfo(Model model, Principal principal) {
		getSideImg(model, principal);

		// USER_ID로 가지고 있는 매장 리스트 뽑기
		List<MemShopMappingDto> myShopList = emplInfoService.getMyShopList(principal.getName());

		model.addAttribute("shopDto", new ShopDto()); // 매장 전체 정보를 가지고 있는 DTO
		model.addAttribute("myShopList", myShopList); // 매장 이름 리스트를 가져옴

		return "manage/employeeInfoForm";
	}

	// 직원정보 불러오기
	@GetMapping(value = "/manage/employeeInfo/{shopId}")
	public String emplInfoDtl(Model model, Principal principal) {
		getSideImg(model, principal);

		// USER_ID로 가지고 있는 매장 리스트 뽑기
		List<MemShopMappingDto> myShopList = emplInfoService.getMyShopList(principal.getName());
		
		// 하 여기서 막힘... 다시 잘 생각해보자...
		/* MemShopMapping memShopMapping = mapRepository.findByShopId(); */

		model.addAttribute("shopDto", new ShopDto()); // 매장 전체 정보를 가지고 있는 DTO
		model.addAttribute("myShopList", myShopList); // 매장 이름 리스트를 가져옴

		return null;
	}

	// 매니저 출근관리 화면
	@GetMapping(value = "/commute")
	public String commute(Model model, Principal principal) {

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("commuteDto", new CommuteDto());

		return "manage/managerCommuteForm";
	}

	// 매니저 급여관리 화면
	@GetMapping(value = "/commute/{shopId}")
	public String getRegister(@PathVariable("shopId") Long shopId, Model model, Principal principal) {

		List<CommuteDto> commuteList = commuteService.getCommuteListForManager(shopId, principal.getName());
		// 매니저 아이디로 매장 목록 띄우기
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		CommuteDto commuteDto = new CommuteDto();

		ShopDto shopDto = new ShopDto();
		shopDto.setShopId(shopId);
		commuteDto.setShopDto(shopDto);

		getSideImg(model, principal);

		model.addAttribute("myShopList", myShopList);
		model.addAttribute("commuteList", commuteList);
		model.addAttribute("commuteDto", commuteDto);

		return "manage/managerCommuteForm";
	}

}
