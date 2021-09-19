package com.jinho.posts;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Posts {

    protected Posts() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToOne(mappedBy = "posts", cascade = ALL, fetch = LAZY, optional = false)
    private PostsDetail detail;

    public Posts(final String title) {
        this.title = title;
    }

    public void updateDetails(final PostsDetail postDetail) {
        this.detail = postDetail;
        postDetail.setPosts(this);
    }
}
