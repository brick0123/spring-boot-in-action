package com.jinho.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.TestBatchConfig;
import com.jinho.domain.Order;
import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = {JdbcBatchItemWriterJobConfig.class, TestBatchConfig.class})
class JdbcBatchItemWriterJobConfigTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void name() throws Exception {
        for (int i = 0; i < 10; i++) {
            productRepository.save(new Product(10_000L, "kubernetes"));
        }

        final JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        final List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(10);
        assertThat(orders.get(0).getAmount()).isEqualTo(10_000L);
        assertThat(orders.get(0).getAddress()).isEqualTo("kubernetes");
    }
}
