//package com.tomorrow.service;
//
//import javax.persistence.EntityNotFoundException;
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.thymeleaf.util.StringUtils;
//
//import com.tomorrow.entity.BoardImg;
//import com.tomorrow.repository.BoardImgRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class BoardImgService {
//	@Value("${boardImgLocation}")
//	private String boardImgLocation;
//	private final BoardImgRepository boardImgRepository;
//	private final FileService fileService;
//	
//	//이미지 저장 메소드
//	public void saveBoardImg(BoardImg boardImg, MultipartFile boardImgFile) throws Exception {
//		String oriImgName = boardImgFile.getOriginalFilename(); //파일 이름
//		String imgName = "";
//		String imgUrl = "";
//		
//		//파일 업로드
//		if(!StringUtils.isEmpty(oriImgName)) {
//			imgName = fileService.uploadFile(boardImgLocation, oriImgName, boardImgFile.getBytes());
//			imgUrl = "/images/board/" + imgName;
//		}
//		
//		//상품 이미지 정보 저장
//		boardImg.updateBoardImg(oriImgName, imgName, imgUrl);
//		boardImgRepository.save(boardImg);
//		
//	}
//	
//	//이미지 업데이트 메소드
//		public void updateBoardImg(Long boardImgId, MultipartFile boardImgFile) throws Exception {
//			if(!boardImgFile.isEmpty()) { //파일이 있으면
//				BoardImg savedBoardImg = boardImgRepository.findById(boardImgId)
//						.orElseThrow(EntityNotFoundException::new);
//				
//				//기존 이미지 파일 삭제
//				if(!StringUtils.isEmpty(savedBoardImg.getBoImgNm())) {
//					fileService.deleteFile(boardImgLocation + "/" + savedBoardImg.getBoImgNm());
//				}
//				
//				//수정된 이미지 파일 업로드
//				String oriImgName = boardImgFile.getOriginalFilename(); 
//				String imgName = fileService.uploadFile(boardImgLocation, oriImgName, boardImgFile.getBytes());
//				String imgUrl = "/images/board/" + imgName;
//				
//				savedBoardImg.updateBoardImg(oriImgName, imgName, imgUrl);
//			}
//		}
//	
//	
//}
