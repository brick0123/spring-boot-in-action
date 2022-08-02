package com.jinho.job;


import com.jinho.AbstractBatchIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleTest extends AbstractBatchIntegrationTest {

    @Test
    @DisplayName("테스트용 심플 잡 실행 확인")
    void 청크() {
        launchJob(SimpleChunk.JOB_NAME);
        checkSuccessJob();
    }
}


