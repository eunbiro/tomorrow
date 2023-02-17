package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPay is a Querydsl query type for Pay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPay extends EntityPathBase<Pay> {

    private static final long serialVersionUID = -1813671271L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPay pay = new QPay("pay");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QManager manager;

    public final QPayList payList;

    public final NumberPath<Integer> timePay = createNumber("timePay", Integer.class);

    public QPay(String variable) {
        this(Pay.class, forVariable(variable), INITS);
    }

    public QPay(Path<? extends Pay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPay(PathMetadata metadata, PathInits inits) {
        this(Pay.class, metadata, inits);
    }

    public QPay(Class<? extends Pay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.manager = inits.isInitialized("manager") ? new QManager(forProperty("manager")) : null;
        this.payList = inits.isInitialized("payList") ? new QPayList(forProperty("payList"), inits.get("payList")) : null;
    }

}

