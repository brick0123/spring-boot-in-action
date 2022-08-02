package com.jinho.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.AbstractBatchIntegrationTest;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StopWatch;

@TestPropertySource(properties = {"chunkSize=5", "poolSize=5"})
class MultiThreadJobConfigTest extends AbstractBatchIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            productRepository.save(new Product(1000L));
        }
    }

    @Test
    void task() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final JobParameters parameters = new JobParametersBuilder()
            .addString("requestDate", LocalDate.now().toString())
            .toJobParameters();

        launchJob(MultiThreadJobConfig.JOB_NAME, parameters);
        stopWatch.stop();

        System.out.println(">>> total task time = " + stopWatch.getTotalTimeMillis() + "ms");

        checkSuccessJob();
        assertThat(orderRepository.count()).isEqualTo(10L);
    }
}
