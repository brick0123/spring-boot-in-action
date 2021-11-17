package com.jinho.config;

import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.QueryMetadata;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAProvider;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class CustomJPAQuery<T> extends AbstractJPAQuery<T, CustomJPAQuery<T>> {

    public CustomJPAQuery() {
        super(null, JPQLTemplates.DEFAULT, new DefaultQueryMetadata());
    }

    /**
     * Creates a new EntityManager bound query
     *
     * @param em entity manager
     */
    public CustomJPAQuery(EntityManager em) {
        super(em, JPAProvider.getTemplates(em), new DefaultQueryMetadata());
    }

    /**
     * Creates a new EntityManager bound query
     *
     * @param em       entity manager
     * @param metadata query metadata
     */
    public CustomJPAQuery(EntityManager em, QueryMetadata metadata) {
        super(em, JPAProvider.getTemplates(em), metadata);
    }

    /**
     * Creates a new query
     *
     * @param em        entity manager
     * @param templates templates
     */
    public CustomJPAQuery(EntityManager em, JPQLTemplates templates) {
        super(em, templates, new DefaultQueryMetadata());
    }

    /**
     * Creates a new query
     *
     * @param em        entity manager
     * @param templates templates
     * @param metadata  query metadata
     */
    public CustomJPAQuery(EntityManager em, JPQLTemplates templates, QueryMetadata metadata) {
        super(em, templates, metadata);
    }

    @Override
    public CustomJPAQuery<T> clone(EntityManager entityManager, JPQLTemplates templates) {
        CustomJPAQuery<T> q = new CustomJPAQuery<T>(entityManager, templates, getMetadata().clone());
        q.clone(this);
        return q;
    }

    @Override
    public CustomJPAQuery<T> clone(EntityManager entityManager) {
        return clone(entityManager, JPAProvider.getTemplates(entityManager));
    }

    @Override
    public <U> CustomJPAQuery<U> select(Expression<U> expr) {
        queryMixin.setProjection(expr);
        @SuppressWarnings("unchecked") // This is the new type
        CustomJPAQuery<U> newType = (CustomJPAQuery<U>) this;
        return newType;
    }

    @Override
    public CustomJPAQuery<Tuple> select(Expression<?>... exprs) {
        queryMixin.setProjection(exprs);
        @SuppressWarnings("unchecked") // This is the new type
        CustomJPAQuery<Tuple> newType = (CustomJPAQuery<Tuple>) this;
        return newType;
    }

    @SuppressWarnings("unchecked")
    public Slice<T> toSlice() {
        try {
            final var origin = getMetadata().getModifiers();
            final var queryModifiers = new QueryModifiers(origin.getLimit() + 1, origin.getOffset());

            final var query = createQuery(queryModifiers, false);

            final var result = (List<T>) getResultList(query);

            boolean hasNext = result.size() > queryModifiers.getLimit();

            return new SliceImpl<>(hasNext ? result.subList(0, origin.getLimitAsInteger()) : result, PageRequest.of(origin.getLimitAsInteger(), origin.getLimitAsInteger()), hasNext);
        } finally {
            reset();
        }
    }

    private List<?> getResultList(Query query) {
        // TODO : use lazy fetch here?
        if (projection != null) {
            List<?> results = query.getResultList();
            List<Object> rv = new ArrayList<Object>(results.size());
            for (Object o : results) {
                if (o != null) {
                    if (!o.getClass().isArray()) {
                        o = new Object[]{o};
                    }
                    rv.add(projection.newInstance((Object[]) o));
                } else {
                    rv.add(null);
                }
            }
            return rv;
        } else {
            return query.getResultList();
        }
    }

}
