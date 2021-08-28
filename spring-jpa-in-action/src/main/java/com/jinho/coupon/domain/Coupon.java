package com.jinho.coupon.domain;

import com.jinho.coupon.exception.InvalidStockAmountException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.Getter;

@Entity
@Getter
public class Coupon {

    protected Coupon() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(nullable = false)
    private String couponNo;

    @Embedded
    private Stock stock;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "coupon")
    private List<CouponHistory> couponHistories = new ArrayList<>();

    @Version
    private Long version;

    public void decreaseRemainAmount() {
        final int afterRemainAmount = stock.getRemainAmount() - 1;
        final int currentLimitAmount = stock.getLimitAmount();

        verifyStockAmount(afterRemainAmount);

        stock = new Stock(afterRemainAmount, currentLimitAmount);
    }

    private void verifyStockAmount(final int afterRemainAmount) {
        if (afterRemainAmount < 0) {
            throw new InvalidStockAmountException();
        }
    }

    public void addHistory(final CouponHistory couponHistory) {
        couponHistories.add(couponHistory);
        couponHistory.setCoupon(this);
    }

    public Coupon(final String couponNo, final Stock stock) {
        this.couponNo = couponNo;
        this.stock = stock;
    }
}
