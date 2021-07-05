package com.jinho.domain;

import static com.jinho.domain.QBoard.board;
import static com.jinho.domain.QBoardBookMark.boardBookMark;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GetBoardListRepository {

    private final JPAQueryFactory queryFactory;

    public Page<BoardResponse> getV1(Long userId, Pageable pageable) {
        List<BoardResponse> content = queryFactory
            .select(Projections.fields(BoardResponse.class,
                board.id.as("boardId"),
                board.title,
                board.content,
                board.createdAt,
                board.updatedAt,
                boardBookMark.id.as("bookMarkId")))
            .from(board)
            .leftJoin(boardBookMark)
            .on(
                boardBookMark.userId.eq(userId),
                boardBookMark.boardId.eq(board.id))
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

}
