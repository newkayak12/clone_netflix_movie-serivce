package com.netflix_clone.userservice.repository.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMobileDeviceInfo is a Querydsl query type for MobileDeviceInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMobileDeviceInfo extends EntityPathBase<MobileDeviceInfo> {

    private static final long serialVersionUID = 1031451678L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMobileDeviceInfo mobileDeviceInfo = new QMobileDeviceInfo("mobileDeviceInfo");

    public final StringPath deviceType = createString("deviceType");

    public final StringPath osVersion = createString("osVersion");

    public final QProfileId profileId;

    public final StringPath pushKey = createString("pushKey");

    public final StringPath uuid = createString("uuid");

    public QMobileDeviceInfo(String variable) {
        this(MobileDeviceInfo.class, forVariable(variable), INITS);
    }

    public QMobileDeviceInfo(Path<? extends MobileDeviceInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMobileDeviceInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMobileDeviceInfo(PathMetadata metadata, PathInits inits) {
        this(MobileDeviceInfo.class, metadata, inits);
    }

    public QMobileDeviceInfo(Class<? extends MobileDeviceInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profileId = inits.isInitialized("profileId") ? new QProfileId(forProperty("profileId"), inits.get("profileId")) : null;
    }

}

