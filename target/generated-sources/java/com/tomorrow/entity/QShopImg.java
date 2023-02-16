package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShopImg is a Querydsl query type for ShopImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopImg extends EntityPathBase<ShopImg> {

    private static final long serialVersionUID = -712746402L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShopImg shopImg = new QShopImg("shopImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath shImgNm = createString("shImgNm");

    public final StringPath shImgUrl = createString("shImgUrl");

    public final QShop shop;

    public final StringPath shOriImgNm = createString("shOriImgNm");

    public QShopImg(String variable) {
        this(ShopImg.class, forVariable(variable), INITS);
    }

    public QShopImg(Path<? extends ShopImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShopImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShopImg(PathMetadata metadata, PathInits inits) {
        this(ShopImg.class, metadata, inits);
    }

    public QShopImg(Class<? extends ShopImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop"), inits.get("shop")) : null;
    }

}

