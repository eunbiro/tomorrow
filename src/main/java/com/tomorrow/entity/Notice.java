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
	@JoinColumn(name = "manager_id")
	private Manager manager;				// 관리자 아이디 FK
	
	@Lob
	@Column(nullable = false)
	private String noticeCont;				// 공지내용

	public static Notice createNotice(NoticeDto noticeDto, Manager manager, Long shopId) {
		
		Notice notice = new Notice();
		Shop shop = new Shop();
		shop.setId(shopId);
		
		notice.setShop(shop);
		notice.setManager(manager);
		notice.setNoticeCont(noticeDto.getNoticeCont());
		return notice;
	}
}
