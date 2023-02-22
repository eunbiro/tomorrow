package com.tomorrow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	@GetMapping(value="/")
	public String main() {
		return "main";
	}
	
	/* TODO :
	1. 버튼을 눌렀을때 내가 가지고 있는 매장 목록을 드롭다운으로 보여준다.
	2. 매장을 클릭했을때 컨트롤러에서 내 회원아이디를 가지고 맴버조회를한다.
	3. 매장클릭링크에 매장코드를 넣어 보내준다.
	4. 매장 코드로 매장조회를한다.
	5. 2번에서 조회한 맴버랑 4번에서 조회한 매장을 가지고 출근.now insert를 한다.

	퇴근은 위와 같은 방법으로 퇴근.now update한다.*/
	
	
	@GetMapping(value="/intro")
	public String intro() {
		return "intro";
	}
}
