
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
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.QMemShopMapping;
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

		return QMemShopMapping.memShopMapping.regTime.after(dateTime);
	}

	private BooleanExpression searchByLike(String checkBy, String checkQuery) {
		if (StringUtils.equals("shopId", checkBy)) {
			return QMemShopMapping.memShopMapping.shop.id.like("%" + checkQuery + "%");
		} else if (StringUtils.equals("timePay", checkBy)) {
			return QMemShopMapping.memShopMapping.timePay.like("%"+checkQuery+"%");
		}
		return null;
	}

	@Override 
	public Page<MemShopMapping> getShopCheckPage(ShopCheckDto shopCheckDto, Pageable pageable) { 
		List<MemShopMapping> content = queryFactory
				.selectFrom(QMemShopMapping.memShopMapping).where(regDtsAfter(shopCheckDto.getCheckDateType()),
						searchByLike(shopCheckDto.getCheckBy(), shopCheckDto.getCheckQuerry()))
				.orderBy(QMemShopMapping.memShopMapping.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		long total = queryFactory.select(Wildcard.count).from(QMemShopMapping.memShopMapping)
				.where(regDtsAfter(shopCheckDto.getCheckDateType()),
						searchByLike(shopCheckDto.getCheckBy(), shopCheckDto.getCheckQuerry()))
				.fetchOne();
		return new PageImpl<>(content,pageable, total);
  }

}
