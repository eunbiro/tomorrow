package com.tomorrow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tomorrow.dto.BoardFormDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommunityController {
	//게시물 리스트 화면 진입
	@GetMapping(value = "/board/list")
	public String boardList() {
		return "community/boardList";
	}
	
	//게시물 상세 화면 진입
	@GetMapping(value = "/board/board_id")
	public String boardDetail() {
		return "community/boardDtl";
	}
	
	//게시물 생성 폼 진입
	@GetMapping(value = "/board/new")
	public String boardNew(Model model) {
		model.addAttribute("boardFormDto", new BoardFormDto());
		return "community/boardForm";
	}
	//수정화면 진입
	@GetMapping(value = "/board/update/{boardId}")
	public String itemDtl(@PathVariable("boardId") Long boardId, Model model) {
		return null;
	}
	
	//게시물 댓글 화면 진입
	@GetMapping(value = "/board/comment/board_id")
	public String boardComment() {
		return "community/boardComment";
	}
	
}
