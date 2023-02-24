package com.tomorrow.service;

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
}
