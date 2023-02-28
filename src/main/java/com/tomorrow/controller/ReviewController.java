package com.tomorrow.controller;

import java.security.Principal;
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

import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ReviewDto;
import com.tomorrow.dto.ReviewListDto;
import com.tomorrow.dto.ReviewSearchDto;
import com.tomorrow.dto.RvCommentDto;
import com.tomorrow.entity.RvComment;
import com.tomorrow.repository.RvCommentRepository;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final MemberService memberService;
	private final RvCommentRepository rvCommentRepository;
	
	@GetMapping(value = "/list")
	public String boardList(ReviewSearchDto reviewSearchDto, Optional<Integer> page, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<ReviewListDto> reviews = reviewService.getReviewListPage(reviewSearchDto, pageable);
		
		model.addAttribute("reviews",reviews);
		model.addAttribute("reviewSearchDto",reviewSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "review/reviewList";
	}
	
	//게시물 상세 화면 진입
	@GetMapping(value = "/{reviewId}")
	public String reviewDetail(Model model, @PathVariable("reviewId") Long reviewId, Principal principal) {
		
		getSideImg(model, principal);
		
		//본문 가져오기
		ReviewDto reviewDto = reviewService.getReviewDtl(reviewId);
		model.addAttribute("review", reviewDto);
		
		//댓글 정보 넣기
		RvCommentDto rvCommentDto = reviewService.setReviewCommentUserInfo(reviewId ,principal.getName());
		model.addAttribute("rvCommentDto", rvCommentDto);
		
		//댓글 불러오기
		List<RvComment> comments = rvCommentRepository.findByReviewIdOrderByIdAsc(reviewId);
		model.addAttribute("comments", comments);
		
		return "review/reviewDtl";
	}
	

	//댓글 생성
	@PostMapping(value="/comment/{reviewId}")
	public String reviewComment(@Valid RvCommentDto rvCommentDto ,@PathVariable("reviewId") Long reviewId, BindingResult bindingResult, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			
			return "redirect:/review/list";
		}
		try {
			
			reviewService.saveRvComment(rvCommentDto);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "댓글 등록 중 에러가 발생했습니다.");
			return "redirect:/review/list";
		}
		
		return "redirect:/review/" + reviewId;
	}
	
	//게시물 생성 폼 진입
	@GetMapping(value = "/new")
	public String reviewNew(Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		ReviewDto reviewDto = reviewService.getReviewUserInfo(principal.getName());		
		model.addAttribute("reviewDto", reviewDto);
		return "review/reviewForm";
	}
	
	// 게시판 생성
	@PostMapping(value = "/new")
	public String reviewNew(@Valid ReviewDto reviewDto, BindingResult bindingResult, Model model, @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			
			return "review/reviewForm";
		}
		try {
			
			reviewService.saveReview(reviewDto, reviewImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "review/reviewForm";
		}
		
		return "redirect:/review/list";
	}
	
	//수정화면 진입
	@GetMapping(value = "/update/{reviewId}")
	public String reviewDtl(@PathVariable("reviewId") Long reviewId, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		try {
			
			ReviewDto reviewDto = reviewService.getReviewDtl(reviewId);
			model.addAttribute("reviewDto", reviewDto);
		} catch(EntityNotFoundException e) {
			
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("reviewDto", new ReviewDto());
			return "review/reviewForm";
		}
		
		return "review/reviewForm";
	}

	// 수정하기 눌렀을 때
	@PostMapping(value = "/update/{reviewId}")
	public String reviewUpdate(@PathVariable("reviewId") Long reviewId, @Valid ReviewDto reviewDto, BindingResult bindingResult, Model model, @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			
			return "review/reviewForm";
		}
		
		reviewDto.setId(reviewId);
		
		try {
			
			reviewService.updateReview(reviewDto, reviewImgFileList);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "게시글 수정 중 에러가 발생하였습니다.");
			reviewDto = reviewService.getReviewDtl(reviewId);
			model.addAttribute("reviewDto", reviewDto);
			return "review/reviewForm";
		}
		return "redirect:/review/list";
	}
	
	//사이드바 프로필 이미지 가져오기 
		public Model getSideImg(Model model, Principal principal) {   
		     MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		     return model.addAttribute("member", memberFormDto);
		}
}
