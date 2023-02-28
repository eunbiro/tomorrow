package com.tomorrow.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.tomorrow.entity.ReviewImg;
import com.tomorrow.repository.ReviewImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgService {

	@Value("${reviewImgLocation}")
	private String reviewImgLocation;
	private final ReviewImgRepository reviewImgRepository;
	private final FileService fileService;
	
	public void saveReviewImg(ReviewImg reviewImg, MultipartFile reviewImgFile) throws Exception {
		
		String oriImgNm = reviewImgFile.getOriginalFilename();
		String imgNm = "";
		String imgUrl = "";
		
		if (!StringUtils.isEmpty(oriImgNm)) {
			
			imgNm =  fileService.uploadFile(reviewImgLocation, oriImgNm, reviewImgFile.getBytes());
			imgUrl = "/images/review/" + imgNm;
		}
		
		reviewImg.updateReviewImg(oriImgNm, imgNm, imgUrl);
		reviewImgRepository.save(reviewImg);
	}

	// 이미지 업데이트 메소드
	public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception {
		
		if (!reviewImgFile.isEmpty()) {
			
			ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId).orElseThrow(EntityNotFoundException::new);
			
			if (!StringUtils.isEmpty(savedReviewImg.getRvImgNm())) {
				
				fileService.deleteFile(reviewImgLocation + "/" + savedReviewImg.getRvImgNm());
			}
			
			String oriImgNm = reviewImgFile.getOriginalFilename();
			String imgNm = fileService.uploadFile(reviewImgLocation, oriImgNm, reviewImgFile.getBytes());
			String imgUrl = "/images/review/" + imgNm;
			
			savedReviewImg.updateReviewImg(oriImgNm, imgNm, imgUrl);
		}
	}
}
