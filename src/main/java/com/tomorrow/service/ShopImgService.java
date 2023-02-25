package com.tomorrow.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.tomorrow.entity.ShopImg;
import com.tomorrow.repository.ShopImgRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class ShopImgService {
	
	@Value("${createShopImgLocation}")
	private String createShopImgLocation;
	 
	private final ShopImgRepository shopImgRepository;
	
	private final FileService fileService;
	
	public void saveShopImg(ShopImg shopImg, MultipartFile createShopImgFile) throws Exception	{
		String shOriImgNm = createShopImgFile.getOriginalFilename();
		String shImgNm="";
		String shImgUrl = "";
		
		if(!StringUtils.isEmpty(shOriImgNm)) {
			shImgNm = fileService.uploadFile(createShopImgLocation, shOriImgNm, createShopImgFile.getBytes());
			shImgUrl = "/images/shop/" + shImgNm;
		}
		shopImg.updateShopImg(shOriImgNm, shImgNm, shImgUrl);
		shopImgRepository.save(shopImg);
	}
	
	// TODO 수경 - 이미지 업데이트하는 메소드 추후에 만들어야 함 
	// 이미지 업데이트 메소드 
	public void updateShopImg(Long shopImgId, MultipartFile shopImgFile) throws Exception {
		if(!shopImgFile.isEmpty()) {
			ShopImg savedShopImg = shopImgRepository.findById(shopImgId)
					.orElseThrow(EntityNotFoundException::new); // 이미지 레코드 찾아오기 
			// 기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedShopImg.getShImgNm())) { // sh_img_name이 있으면
				// c:/tomorrow/shop/[파일이름].jpg
				fileService.deleteFile(createShopImgLocation + "/" + savedShopImg.getShImgNm());
			} 
			
			// 수정된 이미지 파일 업로드 
			String shOriImgNm = shopImgFile.getOriginalFilename(); // 파일 이름
			
			String shImgNm = fileService.uploadFile(createShopImgLocation, shOriImgNm, shopImgFile.getBytes());
			String shImgUrl = "/tomorrow/shop/" + shImgNm;
			
			savedShopImg.updateShopImg(shOriImgNm, shImgNm, shImgUrl);
		}
	}
}
