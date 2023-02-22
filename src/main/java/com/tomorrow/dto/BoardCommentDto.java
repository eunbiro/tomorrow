package com.tomorrow.dto;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Board;
import com.tomorrow.entity.BoardComment;
import com.tomorrow.entity.Member;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardCommentDto {
	private Long id;
	private Member member;	
	private Board board;
	private String boCmtText;
	
	private static ModelMapper modelMapper = new ModelMapper();

	public static BoardCommentDto of(BoardComment boardComment) {
		return modelMapper.map(boardComment, BoardCommentDto.class);
	}
}
