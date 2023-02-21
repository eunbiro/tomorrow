package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeLike is a Querydsl query type for NoticeLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeLike extends EntityPathBase<NoticeLike> {

    private static final long serialVersionUID = 1048365950L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeLike noticeLike = new QNoticeLike("noticeLike");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final QNotice notice;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> upDateTime = _super.upDateTime;

    public QNoticeLike(String variable) {
        this(NoticeLike.class, forVariable(variable), INITS);
    }

    public QNoticeLike(Path<? extends NoticeLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeLike(PathMetadata metadata, PathInits inits) {
        this(NoticeLike.class, metadata, inits);
    }

    public QNoticeLike(Class<? extends NoticeLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice"), inits.get("notice")) : null;
    }

}

