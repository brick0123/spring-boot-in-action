package com.jinho.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Entity
@Setter
@Table(name = "orders")
@ToString
public class Order {

    protected Order() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String address;

    private LocalDate createdAt;

    public Order(final Long amount) {
        this.amount = amount;
    }

    public Order(final LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Order(final Long amount, final String address) {
        this.amount = amount;
        this.address = address;
    }

    public Order(final Long amount, final Product product, final LocalDate createdAt) {
        this.amount = amount;
        this.product = product;
        this.createdAt = createdAt;
    }
}
