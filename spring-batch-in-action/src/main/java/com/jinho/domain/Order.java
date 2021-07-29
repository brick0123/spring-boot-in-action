package com.jinho.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    protected Order() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private LocalDate createdAt;

    public Order(final Long amount) {
        this.amount = amount;
    }

    public Order(final LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
