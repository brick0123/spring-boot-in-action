package com.jinho.domain.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.domain.Club;
import com.jinho.domain.User;
import java.util.List;
import javax.persistence.EntityManager;
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
class ClubQueryRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ClubQueryRepository clubQueryRepository;

    @BeforeEach
    void setup() {
        final User user1 = new User("user1");
        final User user2 = new User("user2");
        final User user3 = new User("user3");
        final User user4 = new User("user4");

        final Club club1 = new Club("club1");
        club1.join(user1);
        club1.join(user2);

        final Club club2 = new Club("club2");
        club2.join(user3);
        club2.join(user4);

        em.persist(club1);
        em.persist(club2);
    }

    @Test
    @DisplayName("distinct 애플리케이션에서 중복을 제거한다.")
    void v1() {
        final List<Club> result = clubQueryRepository.fetchV1();

        assertThat(result.size()).isEqualTo(2);
    }
}
