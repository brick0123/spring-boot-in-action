package com.jinho.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

    public Product() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private String name;

    public Product(final Long amount) {
        this.amount = amount;
    }

    public Product(final Long amount, final String name) {
        this.amount = amount;
        this.name = name;
    }

    public void increaseAmount() {
        amount += 1000;
    }
}
