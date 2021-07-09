package com.jinho.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class BoardBookmark {

    protected BoardBookmark() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long boardId;

    public BoardBookmark(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }
}
