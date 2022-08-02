package com.jinho.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.AbstractBatchIntegrationTest;
import com.jinho.domain.Order;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@SpringBatchTest
//@SpringBootTest(classes = {CompositeItemWriterJobConfig.class, TestBatchConfig.class})
class CompositeItemWriterJobConfigTest extends AbstractBatchIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("product를 업데이트하고 order를 생성하고 저장한다.")
    void 주문_생성() {
        final List<Product> all1 = productRepository.findAll();
        System.out.println("all1 = " + all1);

        final LocalDate today = LocalDate.now();
        for (int i = 0; i < 10; i++) {
            productRepository.save(new Product(i * 1000L, today));
        }

        launchJob(CompositeItemWriterJobConfig.BEAN_NAME);
        checkSuccessJob();

        final List<Product> all = productRepository.findAll();
        assertThat(all.size()).isEqualTo(10);

        final List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(10);
    }
}
