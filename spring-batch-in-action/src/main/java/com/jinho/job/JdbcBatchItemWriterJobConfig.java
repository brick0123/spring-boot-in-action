package com.jinho.job;

import com.jinho.domain.Order;
import com.jinho.domain.Product;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JdbcBatchItemWriterJobConfig {

    private static final String JOB_NAME = "jdbcBatchWrite";
    private static final String BEAN_PREFIX = JOB_NAME + "_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    public static final int chunkSize = 10;

    @Bean
    public Job jdbcBatchItemWriterJob() {
        return jobBuilderFactory.get("jdbcBatchWrite")
            .start(jdbcBatchItemWriterStep())
            .build();
    }

    @Bean(BEAN_PREFIX + "step")
    public Step jdbcBatchItemWriterStep() {
        return stepBuilderFactory.get(BEAN_PREFIX + "step")
            .<Product, Order>chunk(chunkSize)
            .reader(jdbcBatchItemWriterReader())
            .processor(process())
            .writer(jdbcBatchItemWriter())
            .build();
        
    }

    @Bean
    public JdbcCursorItemReader<? extends Product> jdbcBatchItemWriterReader() {
        return new JdbcCursorItemReaderBuilder<Product>()
            .fetchSize(chunkSize)
            .dataSource(dataSource)
            .rowMapper(new BeanPropertyRowMapper<>(Product.class))
            .sql("select product_id, amount, name from product")
            .name(BEAN_PREFIX + "reader")
            .build();
    }

    private ItemProcessor<? super Product, ? extends Order> process() {
        return p -> new Order(p.getAmount(), p.getName());
    }

    @Bean
    public JpaItemWriter<Order> jdbcBatchItemWriter() {
        JpaItemWriter<Order> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
