package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Board;
import com.tomorrow.entity.BoardComment;
import com.tomorrow.entity.Member;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BoardCommentFormDto {
	
	private Long id;
	
	private Member member;
	
	private Board board;
	
	@NotBlank(message = "게시글 내용은 필수 입력 값입니다.")
	private String boCmtText;

	private String createdBy;
	
	private LocalDateTime regTime;
	
	private static ModelMapper modelMapper = new ModelMapper();

	
	public BoardComment createBoardComment() {
		return modelMapper.map(this, BoardComment.class);
	}
	
	public static BoardCommentFormDto of(BoardComment boardComment) {
		return modelMapper.map(boardComment, BoardCommentFormDto.class);
	}
}
