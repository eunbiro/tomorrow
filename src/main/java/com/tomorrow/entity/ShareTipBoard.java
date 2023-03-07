package com.tomorrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tomorrow.dto.ShareTipBoardDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "share_tip_board")
@Getter
@Setter
@ToString
public class ShareTipBoard extends BaseEntity {

	@Id
	@Column(name = "tip_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 매장리뷰 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@Column(length = 50, nullable = false)
	private String tipTitle;				// 매장리뷰 제목
	
	@Lob
	@Column(nullable = false)
	private String tipCont;				// 매장리뷰 내용
	
	public void updateTip(ShareTipBoardDto shareTipBoardDto) {
		
		this.tipTitle = shareTipBoardDto.getTipTitle();
		this.tipCont = shareTipBoardDto.getTipCont();
	}
}
