package com.jinho.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.TestBatchConfig;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = {TestBatchConfig.class, JdbcCursorWithArgumentsJobConfig.class})
class JdbcCursorWithArgumentsJobConfigTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("argument를 전달해서 결과를 조회한다")
    void name() throws Exception {
        for (int i = 0; i < 10; i++) {
            productRepository.save(new Product(i * 1000L, "kubernetes"));
        }

        final JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "kubernetes")
            .addString("amount", "1000")
            .toJobParameters();

        final JobExecution execution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(execution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
