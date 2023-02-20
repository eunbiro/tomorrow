package com.tomorrow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminProfile is a Querydsl query type for AdminProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminProfile extends EntityPathBase<AdminProfile> {

    private static final long serialVersionUID = 2073121929L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminProfile adminProfile = new QAdminProfile("adminProfile");

    public final StringPath adminProNm = createString("adminProNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QManager manager;

    public final StringPath proImgUrl = createString("proImgUrl");

    public final StringPath proOriImgNm = createString("proOriImgNm");

    public QAdminProfile(String variable) {
        this(AdminProfile.class, forVariable(variable), INITS);
    }

    public QAdminProfile(Path<? extends AdminProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdminProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdminProfile(PathMetadata metadata, PathInits inits) {
        this(AdminProfile.class, metadata, inits);
    }

    public QAdminProfile(Class<? extends AdminProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.manager = inits.isInitialized("manager") ? new QManager(forProperty("manager")) : null;
    }

}

