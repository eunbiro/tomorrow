package com.tomorrow.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardFormDto {
	private Long id;
	
	@NotBlank(message = "게시물 제목은 필수 입력 값입니다.")
	private String boardTitle;
	
	@NotBlank(message = "게시글 내용은 필수 입력 값입니다.")
	private String boardCont;
	
	private List<BoardImgDto>boardImgDtoList = new ArrayList<>();
	
	private List<Long> boardImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Board createBoard() {
		return modelMapper.map(this, Board.class);
	}
	
	public static BoardFormDto of(Board board) {
		return modelMapper.map(board, BoardFormDto.class);
	}
}