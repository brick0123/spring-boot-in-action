package com.jinho.coupon.web;

import com.jinho.coupon.domain.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon/{couponNo}")
    public void register(
        @PathVariable final String couponNo
    ) {
        couponService.register(couponNo);
    }
}
