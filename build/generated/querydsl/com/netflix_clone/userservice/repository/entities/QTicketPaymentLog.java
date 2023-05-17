package com.netflix_clone.userservice.repository.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTicketPaymentLog is a Querydsl query type for TicketPaymentLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTicketPaymentLog extends EntityPathBase<TicketPaymentLog> {

    private static final long serialVersionUID = -1051088574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTicketPaymentLog ticketPaymentLog = new QTicketPaymentLog("ticketPaymentLog");

    public final QAccount account;

    public final StringPath originalTransaction = createString("originalTransaction");

    public final DateTimePath<java.time.LocalDateTime> payDay = createDateTime("payDay", java.time.LocalDateTime.class);

    public final NumberPath<Long> payNo = createNumber("payNo", Long.class);

    public final QTicket ticket;

    public QTicketPaymentLog(String variable) {
        this(TicketPaymentLog.class, forVariable(variable), INITS);
    }

    public QTicketPaymentLog(Path<? extends TicketPaymentLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTicketPaymentLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTicketPaymentLog(PathMetadata metadata, PathInits inits) {
        this(TicketPaymentLog.class, metadata, inits);
    }

    public QTicketPaymentLog(Class<? extends TicketPaymentLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
        this.ticket = inits.isInitialized("ticket") ? new QTicket(forProperty("ticket")) : null;
    }

}

