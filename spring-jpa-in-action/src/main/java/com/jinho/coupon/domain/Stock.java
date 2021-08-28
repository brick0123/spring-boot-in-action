package com.jinho.coupon.domain;

import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class Stock {

    protected Stock() {
    }

    private Integer remainAmount;

    private Integer limitAmount;

    public Stock(final Integer remainAmount, final Integer limitAmount) {
        this.remainAmount = remainAmount;
        this.limitAmount = limitAmount;
    }
}
