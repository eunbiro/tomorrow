package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommute is a Querydsl query type for Commute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommute extends EntityPathBase<Commute> {

    private static final long serialVersionUID = -1829393269L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommute commute = new QCommute("commute");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> leaving = createDateTime("leaving", java.time.LocalDateTime.class);

    public final QMember member;

    public final QPayList payList;

    public final QShop shop;

    public final DateTimePath<java.time.LocalDateTime> working = createDateTime("working", java.time.LocalDateTime.class);

    public QCommute(String variable) {
        this(Commute.class, forVariable(variable), INITS);
    }

    public QCommute(Path<? extends Commute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommute(PathMetadata metadata, PathInits inits) {
        this(Commute.class, metadata, inits);
    }

    public QCommute(Class<? extends Commute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.payList = inits.isInitialized("payList") ? new QPayList(forProperty("payList"), inits.get("payList")) : null;
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop")) : null;
    }

}

