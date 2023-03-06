package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Member;
import com.tomorrow.entity.Review;
import com.tomorrow.entity.ShareTipBoard;
import com.tomorrow.entity.ShareTipComment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareTipCommentDto {

	private Long id;
	
	private Member member;
	
	private ShareTipBoard shareTipBoard;
	
	@NotBlank(message = "게시글 내용은 필수 입력 값입니다.")
	private String tipCmtText;
	
	private String createdBy;
	
	private LocalDateTime regTime;
	
	private LocalDateTime upDateTime;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	private List<ShareTipCommentDto> tipCommentDtoList = new ArrayList<>();
	
	private List<Long> tipCommentIds = new ArrayList<>();
	
	public ShareTipComment createTipComment() {
		
		return modelMapper.map(this, ShareTipComment.class);
	}
	
	public static ShareTipCommentDto of (ShareTipBoard shareTipBoard) {
		
		return modelMapper.map(shareTipBoard, ShareTipCommentDto.class);
	}
}
