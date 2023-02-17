package com.tomorrow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommunityController {

	@GetMapping(value = "/board/list")
	public String boardList(Model model) {
		return "community/boardList";
	}
	
}
