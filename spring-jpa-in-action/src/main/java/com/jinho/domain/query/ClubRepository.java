package com.jinho.domain.query;

import static com.jinho.domain.QClub.club;
import static com.jinho.domain.QUser.user;

import com.jinho.domain.Club;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClubRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * distinct는 컬렉션 페치 조인을 사용하면 페이징이 불가.
     * DB에서 모든 데이터를 읽어오고, 메모리에서 페이징 해버림(위험)
     */
    public List<Club> fetchV1() {
        return queryFactory
            .selectFrom(club).distinct()
            .join(club.users, user)
                .fetchJoin()
            .fetch();
    }

    public List<Club> fetchV2() {
        return queryFactory
            .selectFrom(club)
            .fetch();
    }
}
