package com.jinho.domain.query;

import static com.jinho.domain.QClub.club;
import static com.jinho.domain.QQuest.quest;
import static com.jinho.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClubQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClubQueryResponse> findClubQueryResponse() {
        final List<ClubQueryResponse> result = queryFactory
            .select(Projections.fields(ClubQueryResponse.class,
                club.id,
                club.clubName))
            .from(club)
            .fetch(); // 1번의 쿼리

        result
            .forEach(c -> {
                List<UserQueryResponse> responses = findUsers(c.getId()); // n번의 쿼리 발생
                c.setUsers(responses);
            });

        return result;
    }

    private List<UserQueryResponse> findUsers(final Long clubId) {
        return queryFactory
            .select(Projections.fields(UserQueryResponse.class,
                user.name,
                quest.content))
            .from(user)
            .join(user.quest, quest)
            .where(user.club.id.eq(clubId))
            .fetch();
    }
}
