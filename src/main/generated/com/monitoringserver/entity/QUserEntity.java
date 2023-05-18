package com.monitoringserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -1893072765L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final DateTimePath<java.util.Date> userCreatedAt = createDateTime("userCreatedAt", java.util.Date.class);

    public final StringPath userId = createString("userId");

    public final NumberPath<Long> userIdx = createNumber("userIdx", Long.class);

    public final StringPath userPassword = createString("userPassword");

    public final EnumPath<UserRole> userRole = createEnum("userRole", UserRole.class);

    public final StringPath userToken = createString("userToken");

    public final DateTimePath<java.util.Date> userUpdatedAt = createDateTime("userUpdatedAt", java.util.Date.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

