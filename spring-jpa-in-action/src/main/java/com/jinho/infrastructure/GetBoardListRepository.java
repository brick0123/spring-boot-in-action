package com.jinho.infrastructure;

import static com.jinho.domain.QBoard.board;

import com.jinho.domain.BoardResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBoardListRepository {

    private final JPAQueryFactory queryFactory;

    public Page<BoardResponse> getV1(final Pageable pageable) {
        List<BoardResponse> content = queryFactory
            .select(Projections.fields(BoardResponse.class,
                board.id.as("boardId"),
                board.title,
                board.content,
                board.createdAt,
                board.updatedAt))
            .from(board)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, countQuery());
    }

    private long countQuery() {
        return queryFactory
            .selectFrom(board)
            .fetchCount();
    }

    public Slice<BoardResponse> getV2(final Pageable pageable) {

        final List<BoardResponse> content = queryFactory
            .select(Projections.fields(BoardResponse.class,
                board.id.as("boardId"),
                board.title,
                board.content,
                board.createdAt,
                board.updatedAt))
            .from(board)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return RepositoryHelper.toSlice(content, pageable);
    }
}