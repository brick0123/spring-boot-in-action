package com.jinho.job;

import com.jinho.domain.Product;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaCursorItemReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean
    public Job jpaCursorItemReaderJob() {
        return jobBuilderFactory.get("jpaCursorItemReaderJob")
            .start(jpaCursorItemReaderStep())
            .build();
    }

    @Bean
    public Step jpaCursorItemReaderStep() {
        return stepBuilderFactory.get("jpaCursorItemReaderStep")
            .<Product, Product>chunk(chunkSize)
            .reader(jpaCursorItemReader())
            .writer(jpaCursorItemWriter())
            .build();
    }

    @Bean
    public JpaCursorItemReader<Product> jpaCursorItemReader() {
        return new JpaCursorItemReaderBuilder<Product>()
            .name("jpaCursorItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT p FROM Product p")
            .build();
    }

    private ItemWriter<Product> jpaCursorItemWriter() {
        return list -> {
            for (Product product : list) {
                log.info("pay = {}", product);
            }
        };
    }
}
