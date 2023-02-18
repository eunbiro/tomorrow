package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeComment is a Querydsl query type for NoticeComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeComment extends EntityPathBase<NoticeComment> {

    private static final long serialVersionUID = -355797736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeComment noticeComment = new QNoticeComment("noticeComment");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final QNotice notice;

    public final StringPath notiCmtText = createString("notiCmtText");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> upDateTime = _super.upDateTime;

    public QNoticeComment(String variable) {
        this(NoticeComment.class, forVariable(variable), INITS);
    }

    public QNoticeComment(Path<? extends NoticeComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeComment(PathMetadata metadata, PathInits inits) {
        this(NoticeComment.class, metadata, inits);
    }

    public QNoticeComment(Class<? extends NoticeComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice"), inits.get("notice")) : null;
    }

}

