package com.tomorrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.BoardCommentDto;
import com.tomorrow.dto.BoardCommentFormDto;
import com.tomorrow.dto.BoardFormDto;
import com.tomorrow.dto.BoardImgDto;
import com.tomorrow.dto.BoardListDto;
import com.tomorrow.dto.BoardSearchDto;
import com.tomorrow.entity.Board;
import com.tomorrow.entity.BoardComment;
import com.tomorrow.entity.BoardImg;
import com.tomorrow.entity.Member;
import com.tomorrow.repository.BoardCommentRepository;
import com.tomorrow.repository.BoardImgRepository;
import com.tomorrow.repository.BoardRepository;
import com.tomorrow.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
	
	public final BoardRepository boardRepository;
	public final MemberRepository memberRepository;
	public final BoardCommentRepository boardCommentRepository;
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
	@Transactional(readOnly = true) 
	public BoardCommentFormDto getCommentUserInfo(String memberId) {
		// 1. item_img테이블의 이미지를 가져온다.
		Member member = memberRepository.findByUserId(memberId);
		
		BoardCommentFormDto boardCommentFormDto = new BoardCommentFormDto();
		boardCommentFormDto.setMember(member);

		return boardCommentFormDto;
	}
	
	
	//댓글 등록
	public Long saveComment(BoardCommentFormDto boardCommentFormDto) throws Exception {
		BoardComment boardComment = boardCommentFormDto.createBoardComment();
		boardCommentRepository.save(boardComment);
		
		return boardComment.getId();
	}
	
	//게시글 가져오기
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public BoardFormDto getBoardDtl(Long boardId) {
		//해당 게시물에 들어있는 이미지 리스트 불러오기(엔티티)
		List<BoardImg> boardImgList = boardImgRepository.findByBoardIdOrderByIdAsc(boardId);
		List<BoardImgDto> boardImgDtoList = new ArrayList<>();
		
		//엔티티 객체 -> dto객체로 변환
		for(BoardImg boardImg : boardImgList) {
			BoardImgDto boardImgDto = BoardImgDto.of(boardImg);
			boardImgDtoList.add(boardImgDto);
		}
		//게시글가져오기
		Board board= boardRepository.findById(boardId)
				                    .orElseThrow(EntityNotFoundException::new);
	
		BoardFormDto boardFormDto = BoardFormDto.of(board);
		
		boardFormDto.setBoardImgDtoList(boardImgDtoList);
		
		return boardFormDto;
	}
	
	//댓글 가져오기
//		@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
//		public BoardCommentFormDto getCommentList(Long boardId) {
//			List<BoardComment> boardCommentList = boardCommentRepository.findByBoardIdOrderByIdAsc(boardId);
//			List<BoardCommentDto> boardCommentDtoList = new ArrayList<>();
//			
//			for(BoardComment boardComment : boardCommentList) {
//				BoardCommentDto boardCommentDto = BoardCommentDto.of(boardComment);
//				boardCommentDtoList.add(boardCommentDto);
//			}
//			
//			Board board = boardRepository.findById(boardId)
//	                  					 .orElseThrow(EntityNotFoundException::new);
//			BoardCommentFormDto boardCommentFormDto = BoardCommentFormDto.of(board);
//			boardCommentFormDto.setBoardCommentDtoList(boardCommentDtoList);
//			return boardCommentFormDto;
//		}
		
		@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
		public BoardCommentFormDto getCommentList(Long boardId, String memberId) {
			List<BoardComment> boardCommentList = boardCommentRepository.findByBoardIdOrderByIdAsc(boardId);
			List<BoardCommentDto> boardCommentDtoList = new ArrayList<>();
			
			for(BoardComment boardComment : boardCommentList) {
				BoardCommentDto boardCommentDto = BoardCommentDto.of(boardComment);
				boardCommentDtoList.add(boardCommentDto);
			}
			
			Board board = boardRepository.findById(boardId)
	                  					 .orElseThrow(EntityNotFoundException::new);
			BoardCommentFormDto boardCommentFormDto = BoardCommentFormDto.of(board);
			Member member = memberRepository.findByUserId(memberId);
			boardCommentFormDto.setBoardCommentDtoList(boardCommentDtoList);
			boardCommentFormDto.setMember(member);
			return boardCommentFormDto;
		}
		
		
	//게시글 작성자 포함 form 가져오기
		@Transactional(readOnly = true) 
		public BoardFormDto getUserInfo(String memberId) {
			// 1. item_img테이블의 이미지를 가져온다.
			Member member = memberRepository.findByUserId(memberId);
			
			BoardFormDto boardFormDto = new BoardFormDto();
			boardFormDto.setMember(member);

			return boardFormDto;
		}

	//게시글 수정
		public Long updateBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFileList) throws Exception {
			Board board = boardRepository.findById(boardFormDto.getId())
					.orElseThrow(EntityNotFoundException::new);
			
			board.updateBoard(boardFormDto);
			
			List<Long> boardImgIds = boardFormDto.getBoardImgIds(); //상품 이미지 아이디 리스트를 조회
			
			for(int i=0; i<boardImgFileList.size(); i++) {
				boardImgService.updateBoardImg(boardImgIds.get(i), boardImgFileList.get(i));
			}
			
			return board.getId();
		}

	//게시글 리스트 가져오기
		@Transactional(readOnly = true)
		public Page<BoardListDto> getBoardListPage(BoardSearchDto boardSearchDto, Pageable pageable){
			return boardRepository.getBoardListPage(boardSearchDto, pageable);
		}
		
}
