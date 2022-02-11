package com.jinho.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@DiscriminatorValue(value = "book")
@ToString(callSuper = true)
public class Book extends Item {

    protected Book() {
    }

    private String author;

    @Builder
    public Book(int price, String author) {
        super(price);
        this.author = author;
    }
}
