package com.jinho.posts;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostsTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("대상 테이블을 프록시로 가져온다")
    void select_posts() {
        Posts post = new Posts("title");
        PostsDetail postDetails = new PostsDetail("content");
        post.updateDetails(postDetails);

        em.persist(post);

        em.flush();
        em.clear();

        Posts findPost = em.find(Posts.class, post.getId());
        System.out.println(findPost.getDetail().getClass());
    }

    @Test
    @DisplayName("주인 테이블을 프록시로 가져온다")
    void select_postsDetail() {
        Posts post = new Posts("title");
        PostsDetail postDetails = new PostsDetail("content");
        post.updateDetails(postDetails);

        em.persist(post);

        em.flush();
        em.clear();

        PostsDetail findDetail = em.find(PostsDetail.class, postDetails.getId());
    }
}
