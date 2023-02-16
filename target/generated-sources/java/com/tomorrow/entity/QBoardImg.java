package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardImg is a Querydsl query type for BoardImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardImg extends EntityPathBase<BoardImg> {

    private static final long serialVersionUID = 1336060844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardImg boardImg = new QBoardImg("boardImg");

    public final QBoard board;

    public final StringPath boImgNm = createString("boImgNm");

    public final StringPath boImgUrl = createString("boImgUrl");

    public final StringPath boOriImgNm = createString("boOriImgNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBoardImg(String variable) {
        this(BoardImg.class, forVariable(variable), INITS);
    }

    public QBoardImg(Path<? extends BoardImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardImg(PathMetadata metadata, PathInits inits) {
        this(BoardImg.class, metadata, inits);
    }

    public QBoardImg(Class<? extends BoardImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

