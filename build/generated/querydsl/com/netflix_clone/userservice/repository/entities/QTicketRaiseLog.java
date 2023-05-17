package com.netflix_clone.userservice.repository.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTicketRaiseLog is a Querydsl query type for TicketRaiseLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTicketRaiseLog extends EntityPathBase<TicketRaiseLog> {

    private static final long serialVersionUID = 1069959836L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTicketRaiseLog ticketRaiseLog = new QTicketRaiseLog("ticketRaiseLog");

    public final QAccount account;

    public final NumberPath<Long> raiseLog = createNumber("raiseLog", Long.class);

    public final QTicket ticket;

    public final QTicketPaymentLog ticketPaymentLog;

    public QTicketRaiseLog(String variable) {
        this(TicketRaiseLog.class, forVariable(variable), INITS);
    }

    public QTicketRaiseLog(Path<? extends TicketRaiseLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTicketRaiseLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTicketRaiseLog(PathMetadata metadata, PathInits inits) {
        this(TicketRaiseLog.class, metadata, inits);
    }

    public QTicketRaiseLog(Class<? extends TicketRaiseLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
        this.ticket = inits.isInitialized("ticket") ? new QTicket(forProperty("ticket")) : null;
        this.ticketPaymentLog = inits.isInitialized("ticketPaymentLog") ? new QTicketPaymentLog(forProperty("ticketPaymentLog"), inits.get("ticketPaymentLog")) : null;
    }

}

