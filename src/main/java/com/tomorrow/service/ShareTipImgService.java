package com.tomorrow.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.tomorrow.entity.ReviewImg;
import com.tomorrow.entity.ShareTipImg;
import com.tomorrow.repository.ReviewImgRepository;
import com.tomorrow.repository.ShareTipImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShareTipImgService {

	@Value("${sharetipImgLocation}")
	private String sharetipImgLocation;
	private final ShareTipImgRepository shareTipImgRepository;
	private final FileService fileService;
	
	public void saveShareTipImg(ShareTipImg shareTipImg, MultipartFile shareTipImgFile) throws Exception {
		
		String oriImgNm = shareTipImgFile.getOriginalFilename();
		String imgNm = "";
		String imgUrl = "";
		
		if (!StringUtils.isEmpty(oriImgNm)) {
			
			imgNm =  fileService.uploadFile(sharetipImgLocation, oriImgNm, shareTipImgFile.getBytes());
			imgUrl = "/images/sharetip/" + imgNm;
		}
		
		shareTipImg.updateReviewImg(oriImgNm, imgNm, imgUrl);
		shareTipImgRepository.save(shareTipImg);
	}

	// 이미지 업데이트 메소드
	public void updateShareTipImg(Long tipImgId, MultipartFile shareTipImgFile) throws Exception {
		
		if (!shareTipImgFile.isEmpty()) {
			
			ShareTipImg savedShareTipImg = shareTipImgRepository.findById(tipImgId).orElseThrow(EntityNotFoundException::new);
			
			if (!StringUtils.isEmpty(savedShareTipImg.getTipImgNm())) {
				
				fileService.deleteFile(sharetipImgLocation + "/" + savedShareTipImg.getTipImgNm());
			}
			
			String oriImgNm = shareTipImgFile.getOriginalFilename();
			String imgNm = fileService.uploadFile(sharetipImgLocation, oriImgNm, shareTipImgFile.getBytes());
			String imgUrl = "/images/sharetip/" + imgNm;
			
			savedShareTipImg.updateReviewImg(oriImgNm, imgNm, imgUrl);
		}
	}
}
