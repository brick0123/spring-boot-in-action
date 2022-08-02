package com.jinho.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.AbstractBatchIntegrationTest;
import com.jinho.domain.Order;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JdbcBatchItemWriterJobConfigTest extends AbstractBatchIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 기반으로 주문을 생성하고 저장한다.")
    void 상품_기반_주문_생성() {
        for (int i = 0; i < 10; i++) {
            productRepository.save(new Product(10_000L, "kubernetes"));
        }

        launchJob(JdbcBatchItemWriterJobConfig.JOB_NAME);
        checkSuccessJob();

        final List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(10);
        assertThat(orders.get(0).getAmount()).isEqualTo(10_000L);
        assertThat(orders.get(0).getAddress()).isEqualTo("kubernetes");
    }
}
