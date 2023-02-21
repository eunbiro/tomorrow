package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QshopEmpl is a Querydsl query type for shopEmpl
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QshopEmpl extends EntityPathBase<shopEmpl> {

    private static final long serialVersionUID = -685064887L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QshopEmpl shopEmpl = new QshopEmpl("shopEmpl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath partTime = createString("partTime");

    public final QShop shop;

    public final NumberPath<Integer> timePay = createNumber("timePay", Integer.class);

    public QshopEmpl(String variable) {
        this(shopEmpl.class, forVariable(variable), INITS);
    }

    public QshopEmpl(Path<? extends shopEmpl> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QshopEmpl(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QshopEmpl(PathMetadata metadata, PathInits inits) {
        this(shopEmpl.class, metadata, inits);
    }

    public QshopEmpl(Class<? extends shopEmpl> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop"), inits.get("shop")) : null;
    }

}

