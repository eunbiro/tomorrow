package com.tomorrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tomorrow.dto.CreateShopFormDto;
import com.tomorrow.dto.ShopDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "shop")
@Getter
@Setter
@ToString
public class Shop extends BaseEntity{

	@Id
	@Column(name = "shop_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;					// 매장코드
	
	@Column(length = 30, nullable = false)
	private String shopNm;				// 매장 이름
	
	@Column(length = 20)
	private String shopTime;			// 매장 영업시간
	
	@Column(nullable = false)
	private int businessId;				// 사업자번호
	
	@Column(length = 30, nullable = false)
	private String shopPlace;			// 매장위치
	
	@Column(length = 30, nullable = false)
	private String shopType;			// 매장업종
	
	// 매장정보 업데이트
	public void updateShopInfo(CreateShopFormDto createShopFormDto) {
		this.shopNm = createShopFormDto.getShopNm();
		this.shopTime = createShopFormDto.getShopTime();
		this.businessId = createShopFormDto.getBusinessId();
		this.shopPlace = createShopFormDto.getShopPlace();
		this.shopType = createShopFormDto.getShopType();
				
	}
}
