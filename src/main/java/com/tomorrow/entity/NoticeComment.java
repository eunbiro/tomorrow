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
@Table(name = "notice_comment")
@Getter
@Setter
@ToString
public class NoticeComment extends BaseEntity {

	@Id
	@Column(name = "noti_cmt_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 공지댓글 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 아이디 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notice_id")
	private Notice notice;					// 매장공지 식별번호 FK
	
	@Lob
	@Column(nullable = false)
	private String notiCmtText;				// 댓글내용
}
