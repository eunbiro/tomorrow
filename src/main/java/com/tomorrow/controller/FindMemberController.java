package com.tomorrow.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Member;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FindMemberController {

	@Autowired
	private final MemberService memberService;

	// ID찾기화면
	@GetMapping(value = "/find/info")
	public String findMemberInfo(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/findMemberInfo";
	}

	// ID찾기
	@PostMapping(value = "/find/info")
	public String memberFindId(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

		try {
			Member memberFindID = memberService.findByNmPhone(memberFormDto.getUserNm(), memberFormDto.getPNum());
			 System.out.println("==="+ memberFindID.getUserId());
			model.addAttribute("findID", memberFindID);
			//Post방식으로 다음 페이지 넘어감
			return "member/memberFindIdResult";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "일치하는 회원정보가 없습니다.");
			return "member/findMemberInfo";
		}
//			return "member/memberFindIdResult";		
	}

	// id찾기 결과화면
	@GetMapping(value = "/find/result")
	public String memberFindResult() {
		return "member/memberFindIdResult";
	}

	//Id 찾기 페이지에서 Post 방식으로 정보 전달 메소드
	@PostMapping(value = "/find/result")
	public String memberFindResult(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		Member memberFindID = memberService.findByNmPhone(memberFormDto.getUserNm(), memberFormDto.getPNum());
		model.addAttribute("findID", memberFindID);
//		System.out.println(memberFindID.getUserId());
		return "member/memberFindIdResult";
	}

	/*
	 * @PostMapping(value = "/find/info/id")
	 * 
	 * @ResponseBody public ResponseEntity<String> findMemberId(@RequestParam
	 * Map<String, Object> param) { String result = memberService.findId((String)
	 * param.get("name"), (String)param.get("id_phone")); if
	 * (!memberService.isPhoneNum((String)param.get("id_phone"))) { return new
	 * ResponseEntity<String>(result, HttpStatus.BAD_REQUEST); } return new
	 * ResponseEntity<String>(result, HttpStatus.OK); }
	 */

	/*
	 * @PostMapping(value = "/find/info/id")
	 * 
	 * @ResponseBody public String findMemberId(@RequestParam("name") String
	 * name,@RequestParam("id_phone") String id_phone) { String result =
	 * memberService.findId(name, id_phone); if
	 * (!memberService.isPhoneNum(id_phone)) { return "잘못 입력 하셨네요!!" + "<br />" +
	 * "뒤로가서 다시 입력해주세요!"; } return result; }
	 */
	/*
	 * //보류
	 * 
	 * @PostMapping(value = "/find/info/id") public @ResponseBody
	 * ResponseEntity<String> findMemberId(@RequestParam Map<String, String>map) {
	 * String result = memberService.findId(map.get("name"), map.get("id_phone"));
	 * if (!memberService.isPhoneNum((String)map.get("id_phone"))) { return new
	 * ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN); }
	 * 
	 * return new ResponseEntity<String>(result, HttpStatus.OK); }
	 */

	@PostMapping(value = "/find/info/password")
	public String findMemberPw() {
		return "member/modifyPassword";
	}

	@GetMapping(value = "/find/NextPage")
	public String testPage() {
		return "member/modifyPassword";
	}

}
