package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Member;
import com.tomorrow.entity.Review;
import com.tomorrow.entity.RvComment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RvCommentDto {

	private Long id;
	
	private Member member;
	
	private Review review;
	
	@NotBlank(message = "게시글 내용은 필수 입력 값입니다.")
	private String rvCmtText;
	
	private String createdBy;
	
	private LocalDateTime regTime;
	
	private LocalDateTime upDateTime;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	private List<RvCommentDto> rvCommentDtoList = new ArrayList<>();
	
	private List<Long> reviewCommentIds = new ArrayList<>();
	
	public RvComment createRvComment() {
		
		return modelMapper.map(this, RvComment.class);
	}
	
	public static RvCommentDto of (Review review) {
		
		return modelMapper.map(review, RvCommentDto.class);
	}
}
