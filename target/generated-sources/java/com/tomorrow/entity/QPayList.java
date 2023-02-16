package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayList is a Querydsl query type for PayList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayList extends EntityPathBase<PayList> {

    private static final long serialVersionUID = 727499479L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayList payList = new QPayList("payList");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QPayList(String variable) {
        this(PayList.class, forVariable(variable), INITS);
    }

    public QPayList(Path<? extends PayList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayList(PathMetadata metadata, PathInits inits) {
        this(PayList.class, metadata, inits);
    }

    public QPayList(Class<? extends PayList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

