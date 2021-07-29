package com.jinho.job;

import com.jinho.domain.ProductResponse;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GetProductDtoJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean
    public Job getProductDtoJob() {
        return jobBuilderFactory.get("getProductDtoJob")
            .start(getProductDto())
            .build();
    }

    @Bean
    public Step getProductDto() {
        return stepBuilderFactory.get("getProductDto")
            .<ProductResponse, ProductResponse>chunk(chunkSize)
            .reader(getProductDtoReader())
            .writer(getProductDtoWriter())
            .build();
    }

    @Bean
    public JdbcCursorItemReader<ProductResponse> getProductDtoReader() {
        return new JdbcCursorItemReaderBuilder<ProductResponse>()
            .name("getProductDtoReader")
            .dataSource(dataSource)
            .rowMapper(new BeanPropertyRowMapper<>(ProductResponse.class))
            .sql("select p.amount, o.created_at from Product p inner join Orders o on(p.id = o.id)")
            .build();
    }

    private ItemWriter<ProductResponse> getProductDtoWriter() {
        return list -> {
            for (ProductResponse productResponse : list) {
                log.info(">>> product dto = {}", productResponse);
            }
        };
    }

}
