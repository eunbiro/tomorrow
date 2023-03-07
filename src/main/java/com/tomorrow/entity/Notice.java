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

import com.tomorrow.dto.NoticeDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notice")
@Getter
@Setter
@ToString
public class Notice extends BaseEntity {

	@Id
	@Column(name = "notice_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 매장공지 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 아이디 FK
	
	@Lob
	@Column(nullable = false)
	private String noticeCont;				// 공지내용
	
	public static Notice createNotice(NoticeDto noticeDto, Member member, Shop shop) {
		
		Notice notice = new Notice();
		
		notice.setShop(shop);
		notice.setMember(member);
		notice.setNoticeCont(noticeDto.getNoticeCont());
		return notice;
	}
	
	public void updateNotice(NoticeDto noticeDto, Member member, Shop shop) {
		
		this.shop = shop;
		this.member = member;
		this.noticeCont = noticeDto.getNoticeCont();
	}
}
