package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHire is a Querydsl query type for Hire
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHire extends EntityPathBase<Hire> {

    private static final long serialVersionUID = -389465309L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHire hire = new QHire("hire");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath hireCont = createString("hireCont");

    public final StringPath hireTitle = createString("hireTitle");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QManager manager;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final QShop shop;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> upDateTime = _super.upDateTime;

    public QHire(String variable) {
        this(Hire.class, forVariable(variable), INITS);
    }

    public QHire(Path<? extends Hire> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHire(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHire(PathMetadata metadata, PathInits inits) {
        this(Hire.class, metadata, inits);
    }

    public QHire(Class<? extends Hire> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.manager = inits.isInitialized("manager") ? new QManager(forProperty("manager")) : null;
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop"), inits.get("shop")) : null;
    }

}

