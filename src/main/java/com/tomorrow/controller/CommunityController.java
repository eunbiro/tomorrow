package com.tomorrow.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.BoardCommentFormDto;
import com.tomorrow.dto.BoardFormDto;
import com.tomorrow.dto.BoardListDto;
import com.tomorrow.dto.BoardSearchDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.BoardComment;
import com.tomorrow.repository.BoardCommentRepository;
import com.tomorrow.service.BoardService;
import com.tomorrow.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class CommunityController {
	
	private final BoardService boardService;
	private final MemberService memberService;
	private final BoardCommentRepository boardCommentRepostitory;
	//게시물 리스트 화면 진입
	@GetMapping(value = "/list")
	public String boardList(BoardSearchDto boardSearchDto, Optional<Integer> page, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<BoardListDto> boards = boardService.getBoardListPage(boardSearchDto, pageable);
		
		model.addAttribute("boards",boards);
		model.addAttribute("boardSearchDto",boardSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "community/boardList";
	}
	
	//게시물 상세 화면 진입
	@GetMapping(value = "/{boardId}")
	public String boardDetail(Model model, @PathVariable("boardId") Long boardId, Principal principal) {
		
		getSideImg(model, principal);
		//본문 가져오기
		BoardFormDto boardFormDto = boardService.getBoardDtl(boardId);
		model.addAttribute("board", boardFormDto);
		
		//댓글 정보 넣기
		BoardCommentFormDto boardCommentFormDto = boardService.setCommentInfo(boardId ,principal.getName());
		model.addAttribute("boardComment", boardCommentFormDto);
		
		//댓글 불러오기
		List<BoardComment> comments = boardCommentRepostitory.findByBoardIdOrderByIdAsc(boardId);
		model.addAttribute("comments", comments);
		
		return "community/boardDtl";
	}
	
	//댓글 생성
	@PostMapping(value="/comment/{boardId}")
	public String boardComment(@Valid BoardCommentFormDto boardCommentFormDto ,@PathVariable("boardId") Long boardId, BindingResult bindingResult, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			return "redirect:/board/list";
		}
		try {
			boardService.saveComment(boardCommentFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "댓글 등록 중 에러가 발생했습니다.");
			return "redirect:/board/list";
		}
		
		return "redirect:/board/{boardId}";
	}
	
	//게시물 생성 폼 진입
	@GetMapping(value = "/new")
	public String boardNew(Model model, Principal principal) {
		getSideImg(model, principal);
		BoardFormDto boardFormDto = boardService.getUserInfo(principal.getName());		
		model.addAttribute("boardFormDto", boardFormDto);
		return "community/boardForm";
	}
	
	@PostMapping(value = "/new")
	public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, 
			Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, Principal principal) {
		
		getSideImg(model, principal);
		
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
	@ResponseBody
	@GetMapping(value = "/update/{boardId}")
	public String boardDtl(@PathVariable("boardId") Long boardId, Model model, Principal principal) {
		
		getSideImg(model, principal);
		String msg = "";
		
		try {
			if(!boardService.validateBoard(boardId, principal.getName())){
				msg="<script>alert('게시글 수정 권한이 없습니다.'); location.href='/board/list';</script>";
				return msg;
			}
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
	public String boardUpdate(@PathVariable("boardId") Long boardId, @Valid BoardFormDto boardFormDto, BindingResult bindingResult, 
			Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			return "community/boardForm";
		}
		boardFormDto.setId(boardId);
		
		try {
			boardService.updateBoard(boardFormDto, boardImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시글 수정 중 에러가 발생하였습니다.");
			return "community/boardForm";
		}
		return "redirect:/board/list";
	}

	//사이드바 프로필 이미지 가져오기 
	public Model getSideImg(Model model, Principal principal) {   
	     MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
	     return model.addAttribute("member", memberFormDto);
	}
	
	@DeleteMapping(value="/delete/{boardId}")
	public @ResponseBody ResponseEntity deleteBoard(@PathVariable("boardId") Long boardId, Principal principal) {
		if(!boardService.validateBoard(boardId, principal.getName())){
			return new ResponseEntity<String>("게시물 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);		
		}
		
		boardService.deleteBoard(boardId);
		return new ResponseEntity<Long>(boardId, HttpStatus.OK);
	}

}
