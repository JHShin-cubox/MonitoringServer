package com.monitoringserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPcStatusEntity is a Querydsl query type for PcStatusEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPcStatusEntity extends EntityPathBase<PcStatusEntity> {

    private static final long serialVersionUID = -1726974339L;

    public static final QPcStatusEntity pcStatusEntity = new QPcStatusEntity("pcStatusEntity");

    public final StringPath ip = createString("ip");

    public final BooleanPath open = createBoolean("open");

    public final NumberPath<Long> pcIdx = createNumber("pcIdx", Long.class);

    public final StringPath pcStatus = createString("pcStatus");

    public final StringPath time = createString("time");

    public final StringPath type = createString("type");

    public QPcStatusEntity(String variable) {
        super(PcStatusEntity.class, forVariable(variable));
    }

    public QPcStatusEntity(Path<? extends PcStatusEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPcStatusEntity(PathMetadata metadata) {
        super(PcStatusEntity.class, metadata);
    }

}

