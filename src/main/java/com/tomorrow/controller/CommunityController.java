//package com.tomorrow.controller;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.tomorrow.dto.BoardFormDto;
//import com.tomorrow.service.BoardService;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//public class CommunityController {
//	
//	private final BoardService boardService;
//	
//	//게시물 리스트 화면 진입
//	@GetMapping(value = "/board/list")
//	public String boardList() {
//		return "community/boardList";
//	}
//	
//	//게시물 상세 화면 진입
//	@GetMapping(value = "/board/board_id")
//	public String boardDetail() {
//		return "community/boardDtl";
//	}
//		
//	//게시물 생성 폼 진입
//	@GetMapping(value = "/board/new")
//	public String boardNew(Model model) {
//		model.addAttribute("boardFormDto", new BoardFormDto());
//		return "community/boardForm";
//	}
//	
//	@PostMapping(value = "/board/new")
//	public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, 
//			Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList) {
//		
//		if(bindingResult.hasErrors()) {
//			return "community/boardForm";
//		}
//		
//		//첫번째 이미지가 있는지 검사(첫번째 이미지는 필수 입력값이기 때문에)
//		if(boardImgFileList.get(0).isEmpty() && boardFormDto.getId() == null) {
//			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//			return "community/boardForm";
//		}
//	
//		try {
//			boardService.saveBoard(boardFormDto, boardImgFileList);
//		} catch (Exception e) {
//			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
//			return "community/boardForm";
//		}
//		
//		return "redirect:/";
//	}
//	
//	//수정화면 진입
//	@GetMapping(value = "/board/update/{boardId}")
//	public String itemDtl(@PathVariable("boardId") Long boardId, Model model) {
//		return null;
//	}
//	
//	//게시물 댓글 화면 진입
//	@GetMapping(value = "/board/comment/board_id")
//	public String boardComment() {
//		return "community/boardComment";
//	}
//	
//}
