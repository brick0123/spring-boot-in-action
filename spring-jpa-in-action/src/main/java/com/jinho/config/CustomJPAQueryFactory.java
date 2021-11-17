package com.jinho.config;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAUpdateClause;
import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class CustomJPAQueryFactory implements JPQLQueryFactory {

    @Nullable
    private final JPQLTemplates templates;

    private final Provider<EntityManager> entityManager;

    public CustomJPAQueryFactory(final EntityManager entityManager) {
        this.entityManager = new Provider<EntityManager>() {
            @Override
            public EntityManager get() {
                return entityManager;
            }
        };
        this.templates = null;
    }

    public CustomJPAQueryFactory(JPQLTemplates templates, final EntityManager entityManager) {
        this.entityManager = new Provider<EntityManager>() {
            @Override
            public EntityManager get() {
                return entityManager;
            }
        };
        this.templates = templates;
    }

    public CustomJPAQueryFactory(Provider<EntityManager> entityManager) {
        this.entityManager = entityManager;
        this.templates = null;
    }

    public CustomJPAQueryFactory(JPQLTemplates templates, Provider<EntityManager> entityManager) {
        this.entityManager = entityManager;
        this.templates = templates;
    }

    @Override
    public JPADeleteClause delete(EntityPath<?> path) {
        if (templates != null) {
            return new JPADeleteClause(entityManager.get(), path, templates);
        } else {
            return new JPADeleteClause(entityManager.get(), path);
        }
    }

    @Override
    public <T> CustomJPAQuery<T> select(Expression<T> expr) {
        return query().select(expr);
    }

    @Override
    public CustomJPAQuery<Tuple> select(Expression<?>... exprs) {
        return query().select(exprs);
    }

    @Override
    public <T> CustomJPAQuery<T> selectDistinct(Expression<T> expr) {
        return select(expr).distinct();
    }

    @Override
    public CustomJPAQuery<Tuple> selectDistinct(Expression<?>... exprs) {
        return select(exprs).distinct();
    }

    @Override
    public CustomJPAQuery<Integer> selectOne() {
        return select(Expressions.ONE);
    }

    @Override
    public CustomJPAQuery<Integer> selectZero() {
        return select(Expressions.ZERO);
    }

    @Override
    public <T> CustomJPAQuery<T> selectFrom(EntityPath<T> from) {
        return select(from).from(from);
    }

    @Override
    public CustomJPAQuery<?> from(EntityPath<?> from) {
        return query().from(from);
    }

    @Override
    public CustomJPAQuery<?> from(EntityPath<?>... from) {
        return query().from(from);
    }

    @Override
    public JPAUpdateClause update(EntityPath<?> path) {
        if (templates != null) {
            return new JPAUpdateClause(entityManager.get(), path, templates);
        } else {
            return new JPAUpdateClause(entityManager.get(), path);
        }
    }

    @Override
    public JPAInsertClause insert(EntityPath<?> path) {
        if (templates != null) {
            return new JPAInsertClause(entityManager.get(), path, templates);
        } else {
            return new JPAInsertClause(entityManager.get(), path);
        }
    }

    @Override
    public CustomJPAQuery<?> query() {
        if (templates != null) {
            return new CustomJPAQuery<Void>(entityManager.get(), templates);
        } else {
            return new CustomJPAQuery<Void>(entityManager.get());
        }
    }
}
