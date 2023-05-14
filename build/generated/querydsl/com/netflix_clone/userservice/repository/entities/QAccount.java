package com.netflix_clone.userservice.repository.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -474190475L;

    public static final QAccount account = new QAccount("account");

    public final DateTimePath<java.time.LocalDateTime> adultCheckDate = createDateTime("adultCheckDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final BooleanPath isAdult = createBoolean("isAdult");

    public final BooleanPath isSubscribed = createBoolean("isSubscribed");

    public final StringPath mobileNo = createString("mobileNo");

    public final ListPath<Profile, QProfile> profiles = this.<Profile, QProfile>createList("profiles", Profile.class, QProfile.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath userId = createString("userId");

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public final StringPath userPwd = createString("userPwd");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

