package com.tomorrow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/work")
@Controller
@RequiredArgsConstructor
public class WorkController {

	    // 급여일지 폼 
		@GetMapping(value = "/pay")
		public String pay(Model model) {			
			return "work/payForm";
		}
}
