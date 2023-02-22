package com.tomorrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "shop_img")
@Getter
@Setter
@ToString
public class ShopImg extends BaseEntity {

	@Id
	@Column(name = "sh_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;					// 이미지 번호
	
	private String shImgNm;				// 이미지명
	
	private String shOriImgNm;			// 원본 이미지명
	
	private String shImgUrl;			// 이미지경로
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;					// 매장코드 FK
	
	public void updateShopImg(String shOriImgNm, String shImgNm, String shImgUrl) {
		this.shOriImgNm=shOriImgNm;
		this.shImgNm = shImgNm;
		this.shImgUrl = shImgUrl;
	}
}
