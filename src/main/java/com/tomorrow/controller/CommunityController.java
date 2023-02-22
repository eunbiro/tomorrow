package com.tomorrow.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.BoardCommentFormDto;
import com.tomorrow.dto.BoardFormDto;
import com.tomorrow.dto.BoardListDto;
import com.tomorrow.dto.BoardSearchDto;
import com.tomorrow.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class CommunityController {
	
	private final BoardService boardService;
	
	//게시물 리스트 화면 진입
	@GetMapping(value = "/list")
	public String boardList(BoardSearchDto boardSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<BoardListDto> boards = boardService.getBoardListPage(boardSearchDto, pageable);
		
		model.addAttribute("boards",boards);
		model.addAttribute("boardSearchDto",boardSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "community/boardList";
	}
	
	//게시물 상세 화면 진입
	@GetMapping(value = "/{boardId}")
	public String boardDetail(Model model, @PathVariable("boardId") Long boardId) {
		BoardFormDto boardFormDto = boardService.getBoardDtl(boardId);
		model.addAttribute("board", boardFormDto);
		model.addAttribute("boardCommentFormDto", new BoardCommentFormDto());
		return "community/boardDtl";
	}
	
	//댓글 생성
	@PostMapping(value="/{boardId}/comment}")
	public String boardComment(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return "redirect:/board/{boardId}";
		}
		try {
			boardService.saveBoard(boardFormDto, boardImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "redirect:/board/{boardId}";
		}
		
		return "redirect:/board/{boardId}";
	}
	
	//게시물 생성 폼 진입
	@GetMapping(value = "/new")
	public String boardNew(Model model) {
		model.addAttribute("boardFormDto", new BoardFormDto());
		return "community/boardForm";
	}
	
	@PostMapping(value = "/new")
	public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, 
			Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "community/boardForm";
		}
		try {
			boardService.saveBoard(boardFormDto, boardImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "community/boardForm";
		}
		
		return "redirect:/board/list";
	}
	
	//수정화면 진입
	@GetMapping(value = "/update/{boardId}")
	public String boardDtl(@PathVariable("boardId") Long boardId, Model model) {
		try {
			BoardFormDto boardFormdto = boardService.getBoardDtl(boardId);
			model.addAttribute(boardFormdto);
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("campFormDto", new BoardFormDto());
			return "community/boardForm";
		}
		return "community/boardForm";
	}
	
	@PostMapping(value = "/update/{boardId}")
	public String boardUpdate(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, 
			Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList) {
		if(bindingResult.hasErrors()) {
			return "community/boardForm";
		}
		try {
			boardService.updateBoard(boardFormDto, boardImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시글 수정 중 에러가 발생하였습니다.");
			return "community/boardForm";
		}
		return "redirect:/board/list";
	}
	
	//게시물 댓글 화면 진입
	@GetMapping(value = "/board/comment/board_id")
	public String boardComment() {
		return "community/boardComment";
	}
	
}
