package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.tomorrow.dto.ReviewDto;
import com.tomorrow.dto.ReviewImgDto;
import com.tomorrow.dto.ReviewListDto;
import com.tomorrow.dto.ReviewSearchDto;
import com.tomorrow.dto.RvCommentDto;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Review;
import com.tomorrow.entity.ReviewImg;
import com.tomorrow.entity.RvComment;
import com.tomorrow.repository.MemberRepository;
import com.tomorrow.repository.ReviewImgRepository;
import com.tomorrow.repository.ReviewRepository;
import com.tomorrow.repository.RvCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
	
	private final ReviewImgService reviewImgService;
	private final ReviewRepository reviewRepository;
	private final RvCommentRepository rvCommentRepository;
	private final ReviewImgRepository reviewImgRepository;
	private final MemberRepository memberRepository;
	
	// 게시판 등록
	public Long saveReview(ReviewDto reviewDto, List<MultipartFile> reviewImgFileList) throws Exception {
		
		Review review = reviewDto.createReview();
		reviewRepository.save(review);
		
		for (int i = 0; i < reviewImgFileList.size(); i++) {
			
			ReviewImg reviewImg = new ReviewImg();
			reviewImg.setReview(review);
			
			reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
		}
		
		return review.getId();
	}

	// 회원 정보가지고 있는 리뷰게시글dto
	@Transactional(readOnly = true)
	public ReviewDto getReviewUserInfo(String userId) {
		
		Member member = findmember(userId);		
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setMember(member);
		
		return reviewDto;
	}
	
	// 회원 정보가지고 있는 리뷰게시글dto
	@Transactional(readOnly = true)
	public RvCommentDto getReviewCommentUserInfo(String userId) {
		
		Member member = findmember(userId);		
		RvCommentDto rvCommentDto = new RvCommentDto();
		rvCommentDto.setMember(member);
		
		return rvCommentDto;
	}
	
	// 회원 정보가지고 있는 리뷰댓글dto
	public RvCommentDto setReviewCommentUserInfo(Long reviewId, String userId) {
		
		Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
		RvCommentDto rvCommentDto = RvCommentDto.of(review);
		Member member = findmember(userId);
		rvCommentDto.setMember(member);
		
		return rvCommentDto;
	}
	
	// 댓글등록
	public Long saveRvComment(RvCommentDto rvCommentDto) {
		
		RvComment rvComment = rvCommentDto.createRvComment();
		rvCommentRepository.save(rvComment);
		
		return rvComment.getId();
	}
	
	
	// 게시글 가져오기
	@Transactional(readOnly = true)
	public ReviewDto getReviewDtl(Long reviewId) {
		
		List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);
		List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
		
		for (ReviewImg reviewImg : reviewImgList) {
			
			ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
			reviewImgDtoList.add(reviewImgDto);
		}
		
		Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
		
		ReviewDto reviewDto = ReviewDto.of(review);
		reviewDto.setReviewImgDtoList(reviewImgDtoList);
		
		return reviewDto;
	}
	
	// 리뷰업데이트
	public Long updateReview(ReviewDto reviewDto, List<MultipartFile> reviewImgFileList) throws Exception {
		
		Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow(EntityNotFoundException::new);
		review.updateReview(reviewDto);
		
		List<Long> reviewImgIds = reviewDto.getReviewImgIds();
		
		for (int i =0; i < reviewImgFileList.size(); i++) {
			
			reviewImgService.updateReviewImg(reviewImgIds.get(i), reviewImgFileList.get(i));
		}
		
		return review.getId();
	}
	
	// 게시글 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<ReviewListDto> getReviewListPage(ReviewSearchDto reviewSearchDto, Pageable pageable) {
		
		return reviewRepository.getReviewListPage(reviewSearchDto, pageable);
	}
	
	// 멤버가져옴
	public Member findmember(String userId) {
		
		return memberRepository.findByUserId(userId);
	}
	
	// 매장공지 내용을 delete
	public void deleteReview(Long reviewId) {

		Review review  = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
		List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);
		
		for (ReviewImg reviewImg : reviewImgList) {
			
			reviewImgRepository.delete(reviewImg);
		}
		
		List<RvComment> rvCommentList = rvCommentRepository.findByReviewIdOrderByIdAsc(reviewId);
		
		for (RvComment rvComment : rvCommentList) {
			
			rvCommentRepository.delete(rvComment);
		}
		
		reviewRepository.delete(review);
	}
	
}
