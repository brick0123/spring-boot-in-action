package com.jinho.job;

import com.jinho.AbstractBatchIntegrationTest;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

class JdbcCursorWithArgumentsJobConfigTest extends AbstractBatchIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("jobParameter를 reader에서 파라미터로 사용한다")
    void name() {
        for (int i = 0; i < 10; i++) {
            productRepository.save(new Product(i * 1000L, "kubernetes"));
        }

        final JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "kubernetes")
            .addString("amount", "1000")
            .toJobParameters();

        launchJob(JdbcBatchItemWriterJobConfig.JOB_NAME, jobParameters);
        checkSuccessJob();
    }
}
