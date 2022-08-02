package com.jinho.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.AbstractBatchIntegrationTest;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

class ProductDoubleStepJobConfigTest extends AbstractBatchIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("product amount를 증가하는 step을 실행하고 order를 생성하는 step을 실행한다.")
    void 멀티_스텝() {
        for (long i = 0; i < 10; i++) {
            productRepository.save(new Product(i * 1_000));
        }

        final JobParameters jobParameters = new JobParametersBuilder()
            .addString("version", "1")
            .addString("requestDate", LocalDate.now().minusDays(10).toString())
            .toJobParameters();

        launchJob(ProductDoubleStepJobConfig.JOB_NAME, jobParameters);

        assertThat(orderRepository.findAll().size()).isEqualTo(10);
    }
}
