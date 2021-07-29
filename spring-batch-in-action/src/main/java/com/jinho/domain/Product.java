package com.jinho.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Product {

    protected Product() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    public Product(final Long amount) {
        this.amount = amount;
    }

    public void increaseAmount() {
        amount += 1000;
    }
}
