package com.jinho.job;

import static org.junit.jupiter.api.Assertions.*;

import com.jinho.TestBatchConfig;
import com.jinho.domain.Order;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = {GetProductDtoJobConfig.class, TestBatchConfig.class})
class GetProductResponseJobConfigTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("join을 해서 dto로 데이터를 가져온다")
    void name() throws Exception {
        for (int i = 0; i < 10; i++) {
            orderRepository.save(new Order(LocalDate.now()));
            productRepository.save(new Product((long) i * 1000));
        }

        final JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
            .addString("version", "1")
            .toJobParameters();

        jobLauncherTestUtils.launchJob(jobParameters);
    }
}
