package com.jinho.web;

import com.jinho.domain.Board;
import com.jinho.domain.BoardBookMark;
import com.jinho.domain.Category;
import com.jinho.domain.User;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetupDummy {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.setup();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void setup() {
            User user = new User();
            em.persist(user);

            for (int i = 0; i < 500; i++) {
                Board board = new Board(Category.FREE_TALK, "Spring Data Performance Test", "Content");
                BoardBookMark bookMark = new BoardBookMark(user.getId(), board.getId());

                em.persist(board);
                em.persist(bookMark);
            }
        }

    }

}
