package com.jinho.domain;

import java.time.LocalDate;
import javax.persistence.Column;
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
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private String name;

    private LocalDate createdAt;

    public Product(final Long amount) {
        this.amount = amount;
    }

    public Product(final Long amount, final String name) {
        this.amount = amount;
        this.name = name;
    }

    public Product(final Long amount, final LocalDate createdAt) {
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Product increaseAmount() {
        amount += 1000;
        return this;
    }
}
