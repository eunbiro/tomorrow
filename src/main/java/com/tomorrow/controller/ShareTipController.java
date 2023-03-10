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

import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.ReviewDto;
import com.tomorrow.dto.ReviewListDto;
import com.tomorrow.dto.ReviewSearchDto;
import com.tomorrow.dto.RvCommentDto;
import com.tomorrow.dto.ShareTipBoardDto;
import com.tomorrow.dto.ShareTipCommentDto;
import com.tomorrow.dto.ShareTipListDto;
import com.tomorrow.dto.ShareTipSearchDto;
import com.tomorrow.entity.RvComment;
import com.tomorrow.entity.ShareTipComment;
import com.tomorrow.repository.RvCommentRepository;
import com.tomorrow.repository.ShareTipCommentRepository;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.ReviewService;
import com.tomorrow.service.ShareTipBoardService;
import com.tomorrow.service.ShareTipImgService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/sharetip")
@RequiredArgsConstructor
public class ShareTipController {

	private final ShareTipBoardService shareTipBoardService;
	private final MemberService memberService;
	private final ShareTipCommentRepository shareTipCommentRepository;
	
	@GetMapping(value = "/list")
	public String boardList(ShareTipSearchDto shareTipSearchDto, Optional<Integer> page, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<ShareTipListDto> shareTips = shareTipBoardService.getShareTipListPage(shareTipSearchDto, pageable);
		
		model.addAttribute("shareTips",shareTips);
		model.addAttribute("shareTipSearchDto",shareTipSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "shareTipBoard/shareTipList";
	}
	
	//????????? ?????? ?????? ??????
	@GetMapping(value = "/{tipId}")
	public String shareTipDetail(Model model, @PathVariable("tipId") Long tipId, Principal principal) {
		
		getSideImg(model, principal);
		
		//?????? ????????????
		ShareTipBoardDto shareTipBoardDto = shareTipBoardService.getShareTipDtl(tipId);
		model.addAttribute("shareTipDto", shareTipBoardDto);
		
		//?????? ?????? ??????
		ShareTipCommentDto shareTipCommentDto = shareTipBoardService.setShareTipCommentUserInfo(tipId ,principal.getName());
		model.addAttribute("shareTipCommentDto", shareTipCommentDto);
		
		//?????? ????????????
		List<ShareTipComment> comments = shareTipCommentRepository.findByTipIdOrderByIdAsc(tipId);
		model.addAttribute("comments", comments);
		
		return "shareTipBoard/shareTipDtl";
	}
	

	//?????? ??????
	@PostMapping(value="/comment/{tipId}")
	public String shareTipComment(@Valid ShareTipCommentDto shareTipCommentDto, @PathVariable("tipId") Long tipId, BindingResult bindingResult, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			
			return "redirect:/sharetip/" + tipId;
		}
		try {
			
			shareTipBoardService.saveShareTipComment(shareTipCommentDto);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "?????? ?????? ??? ????????? ??????????????????.");
			return "redirect:/sharetip/list";
		}
		
		return "redirect:/sharetip/" + tipId;
	}
	
	//????????? ?????? ??? ??????
	@GetMapping(value = "/new")
	public String shareTipNew(Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		ShareTipBoardDto shareTipBoardDto = shareTipBoardService.getShareTipUserInfo(principal.getName());		
		model.addAttribute("shareTipBoardDto", shareTipBoardDto);
		return "shareTipBoard/shareTipForm";
	}
	
	// ????????? ??????
	@PostMapping(value = "/new")
	public String shareTipNew(@Valid ShareTipBoardDto shareTipBoardDto, BindingResult bindingResult, Model model, @RequestParam("sharetipImgFile") List<MultipartFile> sharetipImgFileList, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			
			return "shareTipBoard/shareTipForm";
		}
		try {
			
			shareTipBoardService.saveShareTip(shareTipBoardDto, sharetipImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "?????? ?????? ??? ????????? ??????????????????.");
			return "shareTipBoard/shareTipForm";
		}
		
		return "redirect:/sharetip/list";
	}
	
	//???????????? ??????
	@GetMapping(value = "/update/{tipId}")
	public String shareTipwDtl(@PathVariable("tipId") Long tipId, Model model, Principal principal) {
		
		getSideImg(model, principal);
		
		try {
			
			ShareTipBoardDto shareTipBoardDto = shareTipBoardService.getShareTipDtl(tipId);
			model.addAttribute("shareTipBoardDto", shareTipBoardDto);
		} catch(EntityNotFoundException e) {
			
			model.addAttribute("errorMessage", "???????????? ?????? ???????????????.");
			model.addAttribute("shareTipBoardDto", new ShareTipBoardDto());
			return "shareTipBoard/shareTipForm";
		}
		
		return "shareTipBoard/shareTipForm";
	}

	// ???????????? ????????? ???
	@PostMapping(value = "/update/{tipId}")
	public String shareTipUpdate(@PathVariable("tipId") Long tipId, @Valid ShareTipBoardDto shareTipBoardDto, BindingResult bindingResult, Model model, @RequestParam("sharetipImgFile") List<MultipartFile> sharetipImgFileList, Principal principal) {
		
		getSideImg(model, principal);
		
		if(bindingResult.hasErrors()) {
			
			return "shareTipBoard/shareTipForm";
		}
		
		shareTipBoardDto.setId(tipId);
		
		try {
			
			shareTipBoardService.updateShareTip(shareTipBoardDto, sharetipImgFileList);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "????????? ?????? ??? ????????? ?????????????????????.");
			shareTipBoardDto = shareTipBoardService.getShareTipDtl(tipId);
			model.addAttribute("shareTipBoardDto", shareTipBoardDto);
			return "review/reviewForm";
		}
		return "redirect:/sharetip/" + tipId;
	}
	
	// ????????? ??????
	@DeleteMapping(value = "/{tipId}/delete")
	public @ResponseBody ResponseEntity deleteShareTip(@PathVariable("tipId") Long tipId) {
		
		shareTipBoardService.deleteShareTip(tipId);
		return new ResponseEntity<Long>(tipId, HttpStatus.OK);
	}
	
	// ?????? ??????
	@DeleteMapping(value = "/comment/{commentId}/delete")
	public @ResponseBody ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
		
		shareTipBoardService.deleteShareTipComment(commentId);
		return new ResponseEntity<Long>(commentId, HttpStatus.OK);
	}
	
	//???????????? ????????? ????????? ???????????? 
		public Model getSideImg(Model model, Principal principal) {   
		     MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
		     return model.addAttribute("member", memberFormDto);
		}
}
