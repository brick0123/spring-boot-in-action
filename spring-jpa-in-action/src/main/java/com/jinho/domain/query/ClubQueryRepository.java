package com.jinho.domain.query;

import static com.jinho.domain.QClub.club;
import static com.jinho.domain.QQuest.quest;
import static com.jinho.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClubQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClubQueryResponse> findClubQueryResponse() {
        final List<ClubQueryResponse> result = findClubs(); // 1번의 쿼리

        result
            .forEach(c -> {
                List<UserQueryResponse> responses = findUsers(c.getId()); // n번의 쿼리 발생 루프를 돌 때마다 발생
                c.setUsers(responses);
            });

        return result;
    }

    private List<ClubQueryResponse> findClubs() {
        return queryFactory
            .select(Projections.fields(ClubQueryResponse.class,
                club.id,
                club.clubName))
            .from(club)
            .fetch();
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

    /**
     * findClubQueryResponse에서 루프를 돌면서 쿼리가 발생하는 것을 해결
     */
    public List<ClubQueryResponse> findDtoOptimization() {
        final List<ClubQueryResponse> result = findClubs();

        final Map<Long, List<UserQueryResponse>> userMap = findUsersMap(toClubIds(result));

        result.forEach(c -> c.setUsers(userMap.get(c.getId())));

        return result;
    }

    private Map<Long, List<UserQueryResponse>> findUsersMap(final List<Long> clubIds) {
        final List<UserQueryResponse> users = queryFactory
            .select(Projections.fields(UserQueryResponse.class,
                user.id.as("userId"),
                user.name,
                quest.content))
            .from(user)
            .join(user.quest, quest)
            .where(user.club.id.in(clubIds))
            .fetch();

        final Map<Long, List<UserQueryResponse>> userMap = users.stream()
            .collect(Collectors.groupingBy(UserQueryResponse::getUserId));
        return userMap;
    }

    private List<Long> toClubIds(final List<ClubQueryResponse> result) {
        return result
            .stream()
            .map(ClubQueryResponse::getId)
            .collect(Collectors.toList());
    }
}
