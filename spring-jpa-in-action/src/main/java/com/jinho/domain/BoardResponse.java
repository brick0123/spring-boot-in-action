package com.jinho.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardResponse {

    public BoardResponse() {
    }

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long bookMarkId;

    public BoardResponse(Board entity) {
        this.boardId = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
