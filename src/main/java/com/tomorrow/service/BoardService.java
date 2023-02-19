package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.dto.ItemImgDto;
import com.myshop.entity.Item;
import com.myshop.entity.ItemImg;
import com.tomorrow.dto.BoardFormDto;
import com.tomorrow.dto.BoardImgDto;
import com.tomorrow.entity.Board;
import com.tomorrow.entity.BoardImg;
import com.tomorrow.repository.BoardImgRepository;
import com.tomorrow.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
	
	public final BoardRepository boardRepository;
	public final BoardImgService boardImgService;
	public final BoardImgRepository boardImgRepository;
	
	public Long saveBoard(BoardFormDto boardFormDto,  List<MultipartFile> boardImgFileList) throws Exception {
		//게시글 등록
		Board board = boardFormDto.createBoard();
		boardRepository.save(board);
		
		//이미지 등록
		for(int i=0; i<boardImgFileList.size(); i++) {
			BoardImg boardImg = new BoardImg();
			boardImg.setBoard(board);
			
			boardImgService.saveBoardImg(boardImg, boardImgFileList.get(i));
		}
		
		return board.getId();
	}
	
	//게시글 가져오기
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public BoardFormDto getBoardDtl(Long boardId) {
		//1. item_img테이블의 이미지를 가져온다.
		List<BoardImg> boardImgList = boardImgRepository.findByBoardIdOrderByIdAsc(boardId);
		List<BoardImgDto> boardImgDtoList = new ArrayList<>();
		
		//엔티티 객체 -> dto객체로 변환
		for(BoardImg boardImg : boardImgList) {
			BoardImgDto boardImgDto = BoardImgDto.of(boardImg);
			boardImgDtoList.add(boardImgDto);
		}
		
		//2. item테이블에 있는 데이터를 가져온다.
		Board board= boardRepository.findById(boardId)
				                  .orElseThrow(EntityNotFoundException::new);
		
		//엔티티 객체 -> dto객체로 변환
		BoardFormDto boardFormDto = BoardFormDto.of(board);
		//상품의 이미지 정보를 넣어준다.
		
		boardFormDto.setBoardImgDtoList(boardImgDtoList);
		
		return boardFormDto;
	}
	
	//게시글 수정
		public Long updateBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFileList) throws Exception {
			
			Board board = boardRepository.findById(boardFormDto.getId())
					.orElseThrow(EntityNotFoundException::new);
			
			board.updateBoard(boardFormDto);
			
			List<Long> itemImgIds = itemFormDto.getItemImgIds(); //상품 이미지 아이디 리스트를 조회
			
			for(int i=0; i<itemImgFileList.size(); i++) {
				itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
			}
			
			return item.getId();
		}

}
