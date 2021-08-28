package com.jinho.coupon.domain;

import com.jinho.coupon.exception.NotExistsCouponNoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public void register(final String couponNo) {
        final Coupon coupon = couponRepository.findByCouponNo(couponNo)
            .orElseThrow(NotExistsCouponNoException::new);

        coupon.decreaseRemainAmount();
        coupon.addHistory(new CouponHistory(coupon));
    }
}
