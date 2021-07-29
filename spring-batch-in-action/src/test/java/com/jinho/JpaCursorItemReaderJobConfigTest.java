package com.jinho;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import com.jinho.job.JpaCursorItemReaderJobConfig;
import org.junit.jupiter.api.AfterEach;
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
@SpringBootTest(classes = {JpaCursorItemReaderJobConfig.class, TestBatchConfig.class})
public class JpaCursorItemReaderJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void tearDown() throws Exception {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("커서로 조회한다")
    void select() throws Exception {
        for (long i = 0; i < 10; i++) {
            productRepository.save(new Product(i * 1_000));
        }

        final JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
            .addString("version", "1")
            .toJobParameters();

        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
