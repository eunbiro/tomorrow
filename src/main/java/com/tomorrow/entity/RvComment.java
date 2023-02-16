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
@Table(name = "rv_comment")
@Getter
@Setter
@ToString
public class RvComment extends BaseEntity {

	@Id
	@Column(name = "rv_cmt_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 매장리뷰댓글 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;					// 매장리뷰 식별번호 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	@Lob
	@Column(nullable = false)
	private String rvCmtText;				// 댓글내용
}
