
package com.tomorrow.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tomorrow.dto.ShopCheckDto;
import com.tomorrow.entity.QShop;
import com.tomorrow.entity.Shop;

public class ShopRepositoryCustomImpl implements ShopRepositoryCustom {
	private JPAQueryFactory queryFactory;

	public ShopRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression regDtsAfter(String checkDateType) {
		LocalDateTime dateTime = LocalDateTime.now();

		if (StringUtils.equals("all", checkDateType) || checkDateType == null)
			return null;
		else if (StringUtils.equals("1d", checkDateType))
			dateTime = dateTime.minusDays(1);
		else if (StringUtils.equals("1w", checkDateType))
			dateTime = dateTime.minusWeeks(1);
		else if (StringUtils.equals("1m", checkDateType))
			dateTime = dateTime.minusMonths(1);
		else if (StringUtils.equals("6m", checkDateType))
			dateTime = dateTime.minusMonths(6);

		return QShop.shop.regTime.after(dateTime);
	}

	private BooleanExpression searchByLike(String checkBy, String checkQuery) {
		if (StringUtils.equals("shopNm", checkBy)) {
			return QShop.shop.shopNm.like("%" + checkQuery + "%");
		} else if (StringUtils.equals("createdBy", checkBy)) {
			return QShop.shop.createdBy.like("%" + checkQuery + "%");
		}
		return null;
	}

	@Override public Page<Shop> getShopCheckPage(ShopCheckDto shopCheckDto, Pageable pageable) { 
		List<Shop> content = queryFactory
				.selectFrom(QShop.shop) .where(regDtsAfter(shopCheckDto.getCheckDateType()),
						searchByLike(shopCheckDto.getCheckBy(), shopCheckDto.getCheckQuerry()))
				.orderBy(QShop.shop.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		long total = queryFactory.select(Wildcard.count).from(QShop.shop)
				.where(regDtsAfter(shopCheckDto.getCheckDateType()),
						searchByLike(shopCheckDto.getCheckBy(), shopCheckDto.getCheckQuerry()))
				.fetchOne();
		return new PageImpl<>(content,pageable, total);
  }

}
