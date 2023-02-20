package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShop is a Querydsl query type for Shop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShop extends EntityPathBase<Shop> {

    private static final long serialVersionUID = -389138651L;

    public static final QShop shop = new QShop("shop");

    public final NumberPath<Integer> businessId = createNumber("businessId", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath shopNm = createString("shopNm");

    public final StringPath shopPlace = createString("shopPlace");

    public final StringPath shopTime = createString("shopTime");

    public final StringPath shopType = createString("shopType");

    public QShop(String variable) {
        super(Shop.class, forVariable(variable));
    }

    public QShop(Path<? extends Shop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShop(PathMetadata metadata) {
        super(Shop.class, metadata);
    }

}

