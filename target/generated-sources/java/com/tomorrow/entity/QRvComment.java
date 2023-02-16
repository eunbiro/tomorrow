package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRvComment is a Querydsl query type for RvComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRvComment extends EntityPathBase<RvComment> {

    private static final long serialVersionUID = -2003333972L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRvComment rvComment = new QRvComment("rvComment");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final QReview review;

    public final StringPath rvCmtText = createString("rvCmtText");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> upDateTime = _super.upDateTime;

    public QRvComment(String variable) {
        this(RvComment.class, forVariable(variable), INITS);
    }

    public QRvComment(Path<? extends RvComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRvComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRvComment(PathMetadata metadata, PathInits inits) {
        this(RvComment.class, metadata, inits);
    }

    public QRvComment(Class<? extends RvComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

