package com.jinho.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardResponse {

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long bookMarkId;
}
