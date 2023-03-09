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
@Table(name = "board_comment")
@Getter
@Setter
@ToString
public class BoardComment extends BaseEntity {

	@Id
	@Column(name = "bo_cmt_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 커뮤니티 댓글 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;					// 커뮤니티 식별번호 FK
	
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String boCmtText;				// 커뮤니티 댓글 내용
}
