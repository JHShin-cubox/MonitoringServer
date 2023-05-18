package com.monitoringserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QXrayEntity is a Querydsl query type for XrayEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QXrayEntity extends EntityPathBase<XrayEntity> {

    private static final long serialVersionUID = -1433874390L;

    public static final QXrayEntity xrayEntity = new QXrayEntity("xrayEntity");

    public final StringPath ip = createString("ip");

    public final StringPath labelId = createString("labelId");

    public final NumberPath<Integer> productIdx = createNumber("productIdx", Integer.class);

    public final StringPath productName = createString("productName");

    public final DateTimePath<java.time.LocalDateTime> regdate = createDateTime("regdate", java.time.LocalDateTime.class);

    public final StringPath score = createString("score");

    public QXrayEntity(String variable) {
        super(XrayEntity.class, forVariable(variable));
    }

    public QXrayEntity(Path<? extends XrayEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QXrayEntity(PathMetadata metadata) {
        super(XrayEntity.class, metadata);
    }

}

