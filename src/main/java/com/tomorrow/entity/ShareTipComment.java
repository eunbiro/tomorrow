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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "share_tip_comment")
@Getter
@Setter
@ToString
public class ShareTipComment extends BaseEntity {

	@Id
	@Column(name = "tip_cmt_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 매장리뷰댓글 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tip_id")
	private ShareTipBoard shareTipBoard;	// 매장리뷰 식별번호 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String tipCmtText;				// 댓글내용
}
