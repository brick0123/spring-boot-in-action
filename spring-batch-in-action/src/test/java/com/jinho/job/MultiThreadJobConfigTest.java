package com.jinho.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.TestBatchConfig;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StopWatch;

@SpringBatchTest
@SpringBootTest(classes = {MultiThreadJobConfig.class, TestBatchConfig.class})
@TestPropertySource(properties = {"chunkSize=1", "poolSize=5"})
class MultiThreadJobConfigTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            productRepository.save(new Product(1000L));
        }
    }

    @Test
    void task() throws Exception {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final JobParameters parameters = new JobParametersBuilder()
            .addString("requestDate", LocalDate.now().toString())
            .toJobParameters();

        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        stopWatch.stop();

        System.out.println(">>> total task time = " + stopWatch.getTotalTimeMillis() + "ms");

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(orderRepository.count()).isEqualTo(10L);
    }
}
