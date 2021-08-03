package com.jinho.job;

import com.jinho.domain.Product;
import java.util.Arrays;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CompositeItemWriterJobConfig {

    private static final String BEAN_NAME = "compositeItemWriter";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    @Value("${chunkSize:1000}")
    private int chunkSize;


    @Bean(BEAN_NAME)
    public Job CompositeItemWriterJob() {
        return jobBuilderFactory.get(BEAN_NAME)
            .start(compositeItemWriterStep())
            .build();
    }

    @Bean
    public Step compositeItemWriterStep() {
        return stepBuilderFactory.get("compositeItemWriterJobStep")
            .<Product, Product>chunk(chunkSize)
            .reader(compositeItemWriterReader())
            .processor(compositeItemWriterProcessor())
            .writer(compositeItem())
            .build();
    }

    @Bean
    public JpaCursorItemReader<Product> compositeItemWriterReader() {
        return new JpaCursorItemReaderBuilder<Product>()
            .name("compositeItemWriterReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select p from Product p")
            .build();
    }

    @Bean
    public ItemProcessor<Product, Product> compositeItemWriterProcessor() {
        return Product::increaseAmount;
    }


    @Bean
    public CompositeItemWriter<Product> compositeItem() {
        final CompositeItemWriter<Product> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(Arrays.asList(updateProduct(), insertOrder()));
        return compositeItemWriter;
    }

    // 단순 예제를 위한 코드
    @Bean
    public JpaItemWriter<Product> updateProduct() {
        final JpaItemWriter<Product> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    // 단순 예제를 위한 코드
    @Bean
    public JdbcBatchItemWriter<Product> insertOrder() {
        return new JdbcBatchItemWriterBuilder<Product>()
            .dataSource(dataSource)
            .sql("INSERT INTO orders(product_id, amount) VALUES (:id, :amount)")
            .beanMapped()
            .build();
    }
}
