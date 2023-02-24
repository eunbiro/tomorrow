package com.tomorrow.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.Commute;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.CommuteService;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ShopService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/work")
@Controller
@RequiredArgsConstructor
public class WorkController {
	private final ShopService shopService;
	private final CommuteService commuteService;
	private final MemberService memberService;

	// 급여일지 페이지
	@GetMapping(value = "/pay")
	public String pay(Model model, Principal principal) {
		getSideImg(model, principal);
		return "work/payForm";
	}

	// 출퇴근기록 페이지
	@GetMapping(value = "/commute")
	public String commute(Model model, Principal principal) {
		// url경로에 페이지가 있으면 해당 페이지를 조회하도록 하고 페이지 번호가 없으면 0페이지를 조회하도록 한다.

		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		getSideImg(model, principal);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("commuteDto", new CommuteDto());

		return "work/commuteForm";
	}

	// GET매장 선택 시 출퇴근기록가져옴
	@GetMapping(value = "/commute/{shopId}")
	public String getRegister(@PathVariable("shopId") Long shopId, Model model, Principal principal) {

		List<CommuteDto> commuteList = commuteService.getCommuteList(shopId);
		List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

		CommuteDto commuteDto = new CommuteDto();

		ShopDto shopDto = new ShopDto();
		shopDto.setShopId(shopId);
		commuteDto.setShopDto(shopDto);

		getSideImg(model, principal);
		model.addAttribute("myShopList", myShopList);
		model.addAttribute("commuteList", commuteList);
		model.addAttribute("commuteDto", commuteDto);

		return "work/commuteForm";
	}

	// 사이드바 프로필정보 가져옴
	public Model getSideImg(Model model, Principal principal) {
		MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		return model.addAttribute("member", memberFormDto);
	}

	// 출근 등록
    @PostMapping(value = "/commute")
	public String commuteCreate(@Valid CommuteDto commuteDto, BindingResult bindingResult, Model model,
			Principal principal) {

		if (bindingResult.hasErrors()) {

			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName()); // 내가 갖고 있는 매장 정보를 가져옴

			getSideImg(model, principal);
			model.addAttribute("myShopList", myShopList);
			return "work/commuteForm";

		}

		try {			
			LocalDateTime date = LocalDateTime.now();
			commuteDto.setWorking(date);
			Member member = shopService.findMember(principal.getName());
			Shop shop = shopService.findShop(commuteDto.getShopDto().getShopId());
			Commute commute = Commute.createCommute(commuteDto, member, shop);
		    commuteService.saveCommute(commute);		    
		    
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "출근 처리 중 에러가 발생했습니다.");			
			return "redirect:/work/commute/" + commuteDto.getShopDto().getShopId();
		}

		return "redirect:/work/commute/" + commuteDto.getShopDto().getShopId();
	}
    
    // 퇴근등록(수정기능)
    @PostMapping(value = "/commute/{commuteId}")
    public String commuteUpdate(@PathVariable("commuteId") Long id, @Valid CommuteDto commuteDto, BindingResult bindingResult, Model model,
			Principal principal) {
    	
    	if (bindingResult.hasErrors()) {

			List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName()); // 내가 갖고 있는 매장 정보를 가져옴

			getSideImg(model, principal);
			model.addAttribute("myShopList", myShopList);
			return "work/commuteForm";
		}
    	
    	LocalDateTime date = LocalDateTime.now();
    	commuteDto.setLeaving(date);
    	
    	
    	Member member = shopService.findMember(principal.getName());
    	Shop shop = shopService.findShop(commuteDto.getShopDto().getShopId());
    	
    	Commute commute = commuteService.findByCommuteId(id); //출퇴근 기록 찾기
    	
    	commute.setLeaving(commuteDto.getLeaving());
    	
    	try {
    		
    		 commuteService.updateCommute(id, commuteDto, member, shop);
    		
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "퇴근 처리 중 에러가 발생했습니다.");			
			return "redirect:/work/commute/" + commuteDto.getShopDto().getShopId();
		}
		return "redirect:/work/commute/" + commuteDto.getShopDto().getShopId();
    }
    
    
}
