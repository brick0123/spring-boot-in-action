package com.jinho.domain.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.domain.Club;
import com.jinho.domain.Quest;
import com.jinho.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class ClubRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubQueryRepository clubQueryRepository;

    @BeforeEach
    void setup() {
        final Quest quest1 = new Quest("a");
        final Quest quest2 = new Quest("a");
        em.persist(quest1);
        em.persist(quest2);

        final User user1 = new User("user1");
        user1.registerQuest(quest1);

        final User user2 = new User("user2");
        user2.registerQuest(quest2);

        final Club club1 = new Club("club1");
        club1.join(user1);
        club1.join(user2);

        em.persist(club1);
        em.clear();
    }

    @Test
    @DisplayName("distinct 애플리케이션에서 collection fetch join 중복을 제거.")
    void v1() {
        final List<Club> result = clubRepository.fetchV1();

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("batch size로 N + 1, 페이징 해결")
    void v2() {
        final List<Club> result = clubRepository.fetchV2();

        final List<ClubResponse> dtos = result.stream()
            .map(ClubResponse::new)
            .collect(Collectors.toList());
    }

    @Test
    @DisplayName("toMany 관계를 별도로 조회해서 처리한다.")
    void v3() {
        final List<ClubQueryResponse> result = clubQueryRepository.findClubQueryResponse();

        assertThat(result.size()).isEqualTo(1);
    }


    @Getter
    static class ClubResponse {
        private final String clubName;
        private final List<UserResponse> users;

        public ClubResponse(Club club) {
            clubName = club.getClubName();
            users = club.getUsers()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
        }
    }

    @Getter
    static class UserResponse {
        private final String name;
        private final String content;
        public UserResponse(final User user) {
            name = user.getName();
            content = user.getQuest().getContent();
        }
    }
}
