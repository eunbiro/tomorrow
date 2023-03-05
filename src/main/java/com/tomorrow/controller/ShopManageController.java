package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.PayListDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.PayList;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.PayListRepository;
import com.tomorrow.entity.Commute;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.CommuteService;
import com.tomorrow.service.EmployeeInfoService;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.PayListService;
import com.tomorrow.service.ShopInfoService;
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
	private final ShopInfoService shopInfoService;
	private final PayListService payListService;
	private final PayListRepository payListRepository;
	
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

		model.addAttribute("myShopList", myShopList); // 소유 중인 매장 이름 리스트를 가져옴
		model.addAttribute("shopDto", new ShopDto()); // 매장 전체 정보를 가지고 있는 DTO

		return "manage/employeeInfoForm";
	}

	// 직원정보 불러오기
	@GetMapping(value = "/manage/employeeInfo/{shopId}")
	public String emplInfoDtl(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		try {
			getSideImg(model, principal);
			
			// USER_ID로 가지고 있는 매장 리스트 뽑기
			List<MemShopMappingDto> myShopList = emplInfoService.getMyShopList(principal.getName());
			model.addAttribute("myShopList", myShopList); // 매장 이름 리스트를 가져옴
			
			// 매장 선택한 후 매장 데이터 가져오기
			Shop shop = shopInfoService.findShop(shopId);
			ShopDto shopDto = shopInfoService.getShop(shop);
			model.addAttribute("shopDto", shopDto);
			
			// 직원 리스트 뽑아오기 
			List<MemShopMappingDto> emplList = emplInfoService.getMappingList(shopId);
			model.addAttribute("emplList", emplList);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "직원 정보를 불러오는 중 에러가 발생했습니다.");
			return "manage/employeeInfoForm";
		}

		return "manage/employeeInfoForm";
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
	public String getCommuteByShop(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		//매니저 아이디로 소유중인 매장 목록 띄우기
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
		model.addAttribute("myShopList", myShopList);
		
		//전체 직원 근태 리스트
		List<Commute> commuteList = commuteService.getCommuteListByShop(shopId);
		model.addAttribute("commuteList", commuteList);
		return "manage/managerCommuteForm";
	}
		
	// 매니저 출근관리 화면
	@GetMapping(value = "/pay")
	public String pay(Model model, Principal principal) {
		getSideImg(model, principal);

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("payListDto", new PayListDto());
		
		return "manage/managerPayForm";
	}
	
	// 매니저 급여관리 화면
	@GetMapping(value = "/pay/{shopId}")
	public String getPayListByShop(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		//매니저 아이디로 소유중인 매장 목록 띄우기
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
		model.addAttribute("myShopList", myShopList);
		
		//전체 직원 급여 리스트
		List<MemShopMapping> msmList = mapRepository.findByShopId(shopId);
		List<PayList> payList = payListService.getPayListByMsm(msmList);
		//payList엔 msmList로 가져온 mapping정보에 담긴 직원들 각각의 급여
		model.addAttribute("payList", payList);
		return "manage/managerPayForm";
	}

}
