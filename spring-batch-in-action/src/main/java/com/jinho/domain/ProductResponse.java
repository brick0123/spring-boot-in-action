package com.jinho.domain;

import java.time.LocalDate;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class ProductResponse {

    public ProductResponse() {
    }

    private Long amount;
    private LocalDate orderCreatedAt;

    public ProductResponse(final Long amount, final LocalDate createdAt) {
        this.amount = amount;
        this.orderCreatedAt = createdAt;
    }

}
