package com.jinho;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.AbstractBatchIntegrationTest.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@EnableBatchProcessing
@Import(TestConfig.class)
public abstract class AbstractBatchIntegrationTest {

    @Autowired
    CustomJobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    ApplicationContext ac;

    JobExecution jobExecution;

    @TestConfiguration
    public static class TestConfig {

        @Bean
        DatabaseCleanup databaseCleanup() {
            return new DatabaseCleanup();
        }
    }

    @AfterEach
    void tearDown() {
        databaseCleanup.execute();
    }

    protected void launchJob(String jobName) {
        final Job job = ac.getBean(jobName, Job.class);
        jobLauncherTestUtils.setJob(job);
        try {
            jobExecution = jobLauncherTestUtils.launchJob();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void launchJob(String jobName, JobParameters jobParameters) {
        final Job job = ac.getBean(jobName, Job.class);
        jobLauncherTestUtils.setJob(job);
        try {
            jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void checkSuccessJob() {
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
