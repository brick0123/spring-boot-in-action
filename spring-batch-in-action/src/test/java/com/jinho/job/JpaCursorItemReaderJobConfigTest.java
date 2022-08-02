package com.jinho.job;

import com.jinho.AbstractBatchIntegrationTest;
import com.jinho.domain.Product;
import com.jinho.domain.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaCursorItemReaderJobConfigTest extends AbstractBatchIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("커서로 조회한다")
    void select() {
        for (long i = 0; i < 10; i++) {
            productRepository.save(new Product(i * 1_000));
        }

        launchJob(JpaCursorItemReaderJobConfig.JOB_NAME);
        checkSuccessJob();
    }
}
