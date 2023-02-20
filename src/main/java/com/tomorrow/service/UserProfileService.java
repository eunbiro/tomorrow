package com.tomorrow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.entity.UserProfile;
import com.tomorrow.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.groovy.parser.antlr4.util.StringUtils;
@Service
@Transactional 
@RequiredArgsConstructor
public class UserProfileService {
	
	@Value("${profileImgLocation}")
	private String userProfileImgLocation;
	
	private final UserProfileRepository userProfileRepository;
	
	private final FileService fileService;
	
	public void saveProfileImg(UserProfile profileImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename(); // 파일 이름
		String imgName = "";
		String imgUrl = "";

		// 파일 업로드
		if (!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(userProfileImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}

		// 상품 이미지 정보 저장
		profileImg.updateUserImg(oriImgName, imgName, imgUrl);
		userProfileRepository.save(profileImg);
	}
	
	// 이미지 업데이트 메소드
		public void updateItemImg(Long profileImgId, MultipartFile itemImgFile) throws Exception {
			if (!itemImgFile.isEmpty()) { // 파일이 있으면
				UserProfile saveProfileImg = userProfileRepository.findById(profileImgId).orElseThrow(EntityNotFoundException::new);

				// 기존 이미지 파일 삭제
				if (!StringUtils.isEmpty(saveProfileImg.get())) {
					// C:/shop/item/{itemImgLocation}
					fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
				}

				// 이미지 파일 업로드
				String oriImgName = itemImgFile.getOriginalFilename(); // 파일 이름
				String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
				String imgUrl = "/images/item/" + imgName;
				
				//*** savedItemImg는 현재 영속상태, 데이터를 변경하는 것만으로 변경감지 기능이 동작하여 트랜잭션이 끝날때 update 쿼리가 실행된다
				//update 쿼리 동작하는 기능 순서는 (영속상태 진입 -> 트랜잭션 (거래라고 생각하면 됨) 끝 -> update 쿼리 실행)
				savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
			}
		}

}
