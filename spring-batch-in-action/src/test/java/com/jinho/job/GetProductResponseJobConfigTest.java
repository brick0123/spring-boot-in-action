package com.jinho.job;

import com.jinho.AbstractBatchIntegrationTest;
import com.jinho.domain.Order;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

class GetProductResponseJobConfigTest extends AbstractBatchIntegrationTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("dto로 데이터를 가져온다")
    void name() {
        for (int i = 0; i < 10; i++) {
            orderRepository.save(new Order(LocalDate.now()));
            productRepository.save(new Product((long) i * 1000));
        }

        final JobParameters jobParameters = new JobParametersBuilder()
            .addString("version", "1")
            .toJobParameters();

        launchJob(GetProductDtoJobConfig.JOB_NAME, jobParameters);
    }
}
