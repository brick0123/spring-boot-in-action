package com.jinho.posts;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class PostsDetail {

    protected PostsDetail() {
    }

    @Id
    private Long id;

    private String content;

    @OneToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Posts posts;

    public PostsDetail(final String content) {
        this.content = content;
    }
}
