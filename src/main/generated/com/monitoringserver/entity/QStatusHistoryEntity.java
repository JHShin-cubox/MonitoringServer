package com.monitoringserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStatusHistoryEntity is a Querydsl query type for StatusHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStatusHistoryEntity extends EntityPathBase<StatusHistoryEntity> {

    private static final long serialVersionUID = 2110458224L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStatusHistoryEntity statusHistoryEntity = new QStatusHistoryEntity("statusHistoryEntity");

    public final BooleanPath connected = createBoolean("connected");

    public final NumberPath<Long> historyIdx = createNumber("historyIdx", Long.class);

    public final StringPath luggageId = createString("luggageId");

    public final QPcStatusEntity pcIdx;

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public QStatusHistoryEntity(String variable) {
        this(StatusHistoryEntity.class, forVariable(variable), INITS);
    }

    public QStatusHistoryEntity(Path<? extends StatusHistoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStatusHistoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStatusHistoryEntity(PathMetadata metadata, PathInits inits) {
        this(StatusHistoryEntity.class, metadata, inits);
    }

    public QStatusHistoryEntity(Class<? extends StatusHistoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pcIdx = inits.isInitialized("pcIdx") ? new QPcStatusEntity(forProperty("pcIdx")) : null;
    }

}

