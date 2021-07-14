package com.jinho.web;

import com.jinho.domain.Board;
import com.jinho.domain.BoardBookmark;
import com.jinho.domain.Category;
import com.jinho.domain.User;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
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
            User user = new User("a");
            em.persist(user);

            for (int i = 0; i < 10_000; i++) {
                Board board = new Board(Category.FREE_TALK, "Spring Data Performance Test", "Content");
                BoardBookmark bookMark = new BoardBookmark(user.getId(), board.getId());

                em.persist(board);
                em.persist(bookMark);
            }
        }

    }

}
