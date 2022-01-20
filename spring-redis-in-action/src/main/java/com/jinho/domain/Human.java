package com.jinho.domain;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class  Human {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private ZonedDateTime createdAt;

    protected Human() {
    }

    public Human(String name) {
        this.name = name;
        createdAt = ZonedDateTime.now();
    }
}
