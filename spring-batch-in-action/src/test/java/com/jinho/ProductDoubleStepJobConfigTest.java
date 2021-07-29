package com.jinho;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.domain.OrderRepository;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import com.jinho.job.ProductDoubleStepJobConfig;
import com.jinho.job.ProductToOrderConvertStep;
import com.jinho.job.UpdateProductAmountStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = {ProductDoubleStepJobConfig.class, UpdateProductAmountStep.class, ProductToOrderConvertStep.class, TestBatchConfig.class})
class ProductDoubleStepJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("product amount를 증가하는 step을 실행하고 order를 생성하는 step을 실행한다.")
    void name() throws Exception {
        for (long i = 0; i < 10; i++) {
            productRepository.save(new Product(i * 1_000));
        }

        final JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
            .addString("version", "1")
            .toJobParameters();

        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(orderRepository.findAll().size()).isEqualTo(10);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
