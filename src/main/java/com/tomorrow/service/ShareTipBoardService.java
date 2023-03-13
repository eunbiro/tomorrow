package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.ShareTipBoardDto;
import com.tomorrow.dto.ShareTipCommentDto;
import com.tomorrow.dto.ShareTipImgDto;
import com.tomorrow.dto.ShareTipListDto;
import com.tomorrow.dto.ShareTipSearchDto;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.ShareTipBoard;
import com.tomorrow.entity.ShareTipComment;
import com.tomorrow.entity.ShareTipImg;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ShareTipBoardRepository;
import com.tomorrow.repository.ShareTipCommentRepository;
import com.tomorrow.repository.ShareTipImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShareTipBoardService {
	
	private final ShareTipImgService shareTipImgService;
	private final ShareTipBoardRepository shareTipBoardRepository;
	private final ShareTipCommentRepository shareTipCommentRepository;
	private final ShareTipImgRepository shareTipImgRepository;
	private final MemberRepository memberRepository;
	
	// 게시판 등록
	public Long saveShareTip(ShareTipBoardDto shareTipBoardDto, List<MultipartFile> shareTipImgFileList) throws Exception {
		
		ShareTipBoard shareTipBoard = shareTipBoardDto.createShareTip();
		shareTipBoardRepository.save(shareTipBoard);
		
		for (int i = 0; i < shareTipImgFileList.size(); i++) {
			
			ShareTipImg shareTipImg = new ShareTipImg();
			shareTipImg.setShareTipBoard(shareTipBoard);
			
			shareTipImgService.saveShareTipImg(shareTipImg, shareTipImgFileList.get(i));
		}
		
		return shareTipBoard.getId();
	}

	// 회원 정보가지고 있는 리뷰게시글dto
	public ShareTipBoardDto getShareTipUserInfo(String userId) {
		
		Member member = findmember(userId);		
		ShareTipBoardDto shareTipBoardDto = new ShareTipBoardDto();
		shareTipBoardDto.setMember(member);
		
		return shareTipBoardDto;
	}
	
	// 회원 정보가지고 있는 리뷰게시글dto
	public ShareTipCommentDto getShareTipCommentUserInfo(String userId) {
		
		Member member = findmember(userId);		
		ShareTipCommentDto shareTipCommentDto = new ShareTipCommentDto();
		shareTipCommentDto.setMember(member);
		
		return shareTipCommentDto;
	}
	
	// 회원 정보가지고 있는 리뷰댓글dto
	@Transactional(readOnly = true)
	public ShareTipCommentDto setShareTipCommentUserInfo(Long tipId, String userId) {
		
		ShareTipBoard shareTipBoard = shareTipBoardRepository.findById(tipId).orElseThrow(EntityNotFoundException::new);
		ShareTipCommentDto shareTipCommentDto = ShareTipCommentDto.of(shareTipBoard);
		Member member = findmember(userId);
		shareTipCommentDto.setMember(member);
		
		return shareTipCommentDto;
	}
	
	// 댓글등록
	public Long saveShareTipComment(ShareTipCommentDto shareTipCommentDto) {
		
		ShareTipComment shareTipComment = shareTipCommentDto.createTipComment();
		shareTipCommentRepository.save(shareTipComment);
		
		return shareTipComment.getId();
	}
	
	// 댓글삭제
	public Long deleteShareTipComment(Long commentId) {
		
		ShareTipComment shareTipComment = shareTipCommentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
		shareTipCommentRepository.delete(shareTipComment);
		
		return shareTipComment.getId();
	}
	
	// 게시글 가져오기
	@Transactional(readOnly = true)
	public ShareTipBoardDto getShareTipDtl(Long tipId) {
		
		List<ShareTipImg> shareTipImgList = shareTipImgRepository.findByTipIdOrderByIdAsc(tipId);
		List<ShareTipImgDto> shareTipImgDtoList = new ArrayList<>();
		
		for (ShareTipImg shareTipImg : shareTipImgList) {
			
			ShareTipImgDto shareTipImgDto = ShareTipImgDto.of(shareTipImg);
			shareTipImgDtoList.add(shareTipImgDto);
		}
		
		ShareTipBoard shareTipBoard = shareTipBoardRepository.findById(tipId).orElseThrow(EntityNotFoundException::new);
		
		ShareTipBoardDto shareTipBoardDto = ShareTipBoardDto.of(shareTipBoard);
		shareTipBoardDto.setShareTipImgDtoList(shareTipImgDtoList);
		
		return shareTipBoardDto;
	}
	
	// 리뷰업데이트
	public Long updateShareTip(ShareTipBoardDto shareTipBoardDto, List<MultipartFile> shareTipImgFileList) throws Exception {
		
		ShareTipBoard shareTipBoard = shareTipBoardRepository.findById(shareTipBoardDto.getId()).orElseThrow(EntityNotFoundException::new);
		shareTipBoard.updateTip(shareTipBoardDto);
		
		List<Long> tipImgIds = shareTipBoardDto.getTipImgIds();
		
		for (int i =0; i < shareTipImgFileList.size(); i++) {
			
			shareTipImgService.updateShareTipImg(tipImgIds.get(i), shareTipImgFileList.get(i));
		}
		
		return shareTipBoard.getId();
	}
	
	// 게시글 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<ShareTipListDto> getShareTipListPage(ShareTipSearchDto shareTipSearchDto, Pageable pageable) {
		
		return shareTipBoardRepository.getShareTipListPage(shareTipSearchDto, pageable);
	}
	
	// 멤버가져옴
	@Transactional(readOnly = true)
	public Member findmember(String userId) {
		
		return memberRepository.findByUserId(userId);
	}
	
	// 매장공지 내용을 delete
	public void deleteShareTip(Long tipId) {

		ShareTipBoard shareTipBoard  = shareTipBoardRepository.findById(tipId).orElseThrow(EntityNotFoundException::new);
		List<ShareTipImg> shareTipImgList = shareTipImgRepository.findByTipIdOrderByIdAsc(tipId);
		
		for (ShareTipImg shareTipImg : shareTipImgList) {
			
			shareTipImgRepository.delete(shareTipImg);
		}
		
		List<ShareTipComment> shareTipCommentList = shareTipCommentRepository.findByTipIdOrderByIdAsc(tipId);
		
		for (ShareTipComment shareTipComment : shareTipCommentList) {
			
			shareTipCommentRepository.delete(shareTipComment);
		}
		
		shareTipBoardRepository.delete(shareTipBoard);
	}
	
}
