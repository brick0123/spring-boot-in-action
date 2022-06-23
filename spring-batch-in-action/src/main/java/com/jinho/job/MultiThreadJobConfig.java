package com.jinho.job;

import com.jinho.domain.Order;
import com.jinho.domain.Product;
import com.jinho.job.parameter.RequestDateJobParameter;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MultiThreadJobConfig {

    public static final String JOB_NAME = "multiThreadJob";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final RequestDateJobParameter requestDateJobParameter;

    private int chunkSize;

    @Value("${chunkSize:1000}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    private int poolSize;

    @Value("${poolSize:10}")
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }


    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
            .start(step())
            .incrementer(new RunIdIncrementer())
            .preventRestart()
            .build();
    }

    @Bean(BEAN_PREFIX + "step")
    public Step step() {
        return stepBuilderFactory.get(BEAN_PREFIX + "step")
            .<Product, Order>chunk(chunkSize)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .taskExecutor(taskExecutor())
            .throttleLimit(poolSize)
            .build();
    }


    @Bean(BEAN_PREFIX + "reader")
    @StepScope
    public JpaPagingItemReader<Product> reader() {
        final LocalDate requestDate = requestDateJobParameter.getRequestDate();
        log.info(">>> requestDate = {}", requestDate);
        return new JpaPagingItemReaderBuilder<Product>()
            .name(BEAN_PREFIX + "reader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT p FROM Product p")
            .saveState(false)
            .build();
    }

    @Bean(BEAN_PREFIX + "processor")
    @StepScope
    public ItemProcessor<Product, Order> processor() {
        return p -> {
            TimeUnit.MILLISECONDS.sleep(500L);
            log.info(">>> process");
            return new Order(requestDateJobParameter.getRequestDate());
        };
    }

    @Bean(BEAN_PREFIX + "writer")
    public JpaItemWriter<Order> writer() {
        return new JpaItemWriterBuilder<Order>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("woody-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();
        return executor;
    }
}
