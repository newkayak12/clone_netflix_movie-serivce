package com.netflix_clone.userservice.repository.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTicket is a Querydsl query type for Ticket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTicket extends EntityPathBase<Ticket> {

    private static final long serialVersionUID = -1959657788L;

    public static final QTicket ticket = new QTicket("ticket");

    public final BooleanPath isSupportHDR = createBoolean("isSupportHDR");

    public final EnumPath<com.netflix_clone.userservice.component.enums.Resolution> maximumResolution = createEnum("maximumResolution", com.netflix_clone.userservice.component.enums.Resolution.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> savableCount = createNumber("savableCount", Integer.class);

    public final NumberPath<Long> ticketNo = createNumber("ticketNo", Long.class);

    public final EnumPath<com.netflix_clone.userservice.component.enums.TicketType> type = createEnum("type", com.netflix_clone.userservice.component.enums.TicketType.class);

    public final NumberPath<Integer> watchableSimultaneously = createNumber("watchableSimultaneously", Integer.class);

    public QTicket(String variable) {
        super(Ticket.class, forVariable(variable));
    }

    public QTicket(Path<? extends Ticket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTicket(PathMetadata metadata) {
        super(Ticket.class, metadata);
    }

}

