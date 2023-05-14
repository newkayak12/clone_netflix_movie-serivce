package com.netflix_clone.userservice.repository.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfileId is a Querydsl query type for ProfileId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProfileId extends BeanPath<ProfileId> {

    private static final long serialVersionUID = 391118316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileId profileId = new QProfileId("profileId");

    public final QProfile profile;

    public QProfileId(String variable) {
        this(ProfileId.class, forVariable(variable), INITS);
    }

    public QProfileId(Path<? extends ProfileId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfileId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfileId(PathMetadata metadata, PathInits inits) {
        this(ProfileId.class, metadata, inits);
    }

    public QProfileId(Class<? extends ProfileId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profile = inits.isInitialized("profile") ? new QProfile(forProperty("profile"), inits.get("profile")) : null;
    }

}

