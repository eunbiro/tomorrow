package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkLog is a Querydsl query type for WorkLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkLog extends EntityPathBase<WorkLog> {

    private static final long serialVersionUID = -1254670364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkLog workLog = new QWorkLog("workLog");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath logCont = createString("logCont");

    public final QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> upDateTime = _super.upDateTime;

    public QWorkLog(String variable) {
        this(WorkLog.class, forVariable(variable), INITS);
    }

    public QWorkLog(Path<? extends WorkLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkLog(PathMetadata metadata, PathInits inits) {
        this(WorkLog.class, metadata, inits);
    }

    public QWorkLog(Class<? extends WorkLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

