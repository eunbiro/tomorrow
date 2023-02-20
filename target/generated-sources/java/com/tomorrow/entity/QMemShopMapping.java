package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemShopMapping is a Querydsl query type for MemShopMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemShopMapping extends EntityPathBase<MemShopMapping> {

    private static final long serialVersionUID = -236561070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemShopMapping memShopMapping = new QMemShopMapping("memShopMapping");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath partTime = createString("partTime");

    public final QShop shop;

    public final NumberPath<Integer> timePay = createNumber("timePay", Integer.class);

    public final NumberPath<Integer> workStatus = createNumber("workStatus", Integer.class);

    public QMemShopMapping(String variable) {
        this(MemShopMapping.class, forVariable(variable), INITS);
    }

    public QMemShopMapping(Path<? extends MemShopMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemShopMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemShopMapping(PathMetadata metadata, PathInits inits) {
        this(MemShopMapping.class, metadata, inits);
    }

    public QMemShopMapping(Class<? extends MemShopMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop")) : null;
    }

}

