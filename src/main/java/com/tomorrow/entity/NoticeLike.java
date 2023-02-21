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
@Table(name = "notice_like")
@Getter
@Setter
@ToString
public class NoticeLike extends BaseEntity {

	@Id
	@Column(name = "noti_like_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 공지댓글 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 아이디 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notice_id")
	private Notice notice;					// 매장공지 식별번호 FK
	
	@Column(nullable = false)
	private int likeCount;					// 좋아요 카운트
	
	public static NoticeLike createNoticeLike(Member member, Notice notice) {
		
		NoticeLike noticeLike = new NoticeLike();
		
		noticeLike.setMember(member);
		noticeLike.setNotice(notice);
		
		return noticeLike;
	}
}
